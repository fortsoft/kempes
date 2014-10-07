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
package ro.fortsoft.kempes;

import java.net.URL;

/**
 * @author Decebal Suiu
 */
public class UrlUtils {

    public static String getHost(String url) {
        if (url == null || url.isEmpty()) {
            return "";
        }

        int doubleSlash = url.indexOf("//");
        if (doubleSlash == -1) {
            doubleSlash = 0;
        } else {
            doubleSlash += 2;
        }

        int end = url.indexOf('/', doubleSlash);
        end = (end >= 0) ? end : url.length();

        int port = url.indexOf(':', doubleSlash);
        end = (port > 0 && port < end) ? port : end;

        return url.substring(doubleSlash, end);
    }

    public static String getBaseDomain(String url) {
        String host = getHost(url);

        int startIndex = 0;
        int nextIndex = host.indexOf('.');
        int lastIndex = host.lastIndexOf('.');
        while (nextIndex < lastIndex) {
            startIndex = nextIndex + 1;
            nextIndex = host.indexOf('.', startIndex);
        }

        return (startIndex > 0) ? host.substring(startIndex) : host;
    }

    public static String ensureAbsoluteUrl(String base, String maybeRelative) throws Exception {
        if (maybeRelative.startsWith("http")) {
            return maybeRelative;
        }

        return new URL(new URL(base), maybeRelative).toExternalForm();
    }

}
