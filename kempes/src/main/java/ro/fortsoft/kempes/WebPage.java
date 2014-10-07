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

import org.jsoup.nodes.Document;

/**
 * @author Decebal Suiu
 */
public class WebPage {

    private final String url;
//    private byte[] content;
    private Document document;
    private final String baseDomain;
    private final String host;

    public WebPage(String url) {
        this.url = url;

        baseDomain = UrlUtils.getBaseDomain(url);
        host = UrlUtils.getHost(url);
    }

    public String getUrl() {
        return url;
    }

    public String getBaseDomain() {
        return baseDomain;
    }

    public String getHost() {
        return host;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return "WebPage{" +
                "url='" + url + '\'' +
                ", baseDomain='" + baseDomain + '\'' +
                ", host='" + host + '\'' +
                '}';
    }

}
