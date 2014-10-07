/*
 * Copyright 2014 FortSoft
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package ro.fortsoft.kempes.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Decebal Suiu
 */
public class DefaultEventBus implements EventBus {

    private static final Logger log = LoggerFactory.getLogger(DefaultEventBus.class);

    private Map<Class<?>, Set<HandlerMetaData>> handlers;

    public DefaultEventBus() {
        handlers = new HashMap<Class<?>, Set<HandlerMetaData>>();
    }

    @Override
    public void post(Object event) {
        log.debug("Post event {}", event);
        Set<HandlerMetaData> typeHandlers = handlers.get(event.getClass());
        if (typeHandlers == null) {
            log.info("No handler for event type {}", event.getClass().getName());
            return;
        }

        Collection<HandlerMetaData> invalidHandlers = new LinkedList<HandlerMetaData>();
        for (HandlerMetaData handlerMetaData : typeHandlers) {
            boolean handled = false;
            Object handler = handlerMetaData.handler.get();
            if (handler != null) {
                try {
                    handlerMetaData.method.invoke(handler, event);
                    handled = true;
                } catch (Exception e) {
                    log.debug(e.getMessage(), e);
                }
            }
            if (!handled) {
                invalidHandlers.add(handlerMetaData);
            }
        }

        for (HandlerMetaData handlerMetaData : invalidHandlers) {
            typeHandlers.remove(handlerMetaData);
        }

        if (typeHandlers.isEmpty()) {
            handlers.remove(event.getClass());
        }
    }

    @Override
    public void register(final Object object) {
        log.debug("Register handler {}", object);
        for (Method method : object.getClass().getMethods()) {
            if (method.getAnnotation(Subscribe.class) != null) {
                Class<?>[] paramTypes = method.getParameterTypes();
                if (paramTypes.length == 1) {
                    addTypeSpecificHandler(object, paramTypes[0], method);
                }
            }
        }
    }

    private void addTypeSpecificHandler(final Object handler, final Class<?> type, Method method) {
        Set<HandlerMetaData> typeHandlers = handlers.get(type);
        if (typeHandlers == null) {
            typeHandlers = new HashSet<HandlerMetaData>();
            handlers.put(type, typeHandlers);
        }
        HandlerMetaData handlerMetaData = new HandlerMetaData();
        handlerMetaData.handler = new WeakReference<Object>(handler);
        handlerMetaData.method = method;
        // add the listener
        typeHandlers.add(handlerMetaData);
    }

    private class HandlerMetaData {

        public WeakReference<Object> handler;
        public Method method;

    }

}
