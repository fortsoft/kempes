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

/**
 * @author Decebal Suiu
 */
public interface EventBus {

    /**
     * Posts an event to all registered handlers.
     * @param event
     */
    public void post(Object event);

    /**
     * Registers all handler methods on object to receive events and producer methods to provide events.
     * @param object
     */
    public void register(Object object);

}
