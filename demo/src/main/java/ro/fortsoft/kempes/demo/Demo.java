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
package ro.fortsoft.kempes.demo;

import ro.fortsoft.kempes.WebScraper;

/**
 * In this test/demo application I want to extract all products from category "tablete" from http://www.evomag.ro.
 *
 * @author Decebal Suiu
 */
public class Demo {

	public static void main(String[] args) {
        // uncomment this line if you use a proxy
        setProxy();

        WebScraper scraper = new EvomagScraper("tablete");
        scraper.init();
        scraper.start();
    }

    private static void setProxy() {
        // HTTP/HTTPS Proxy
        System.setProperty("http.proxyHost", "192.168.16.1");
        System.setProperty("http.proxyPort", "128");
    }

}
