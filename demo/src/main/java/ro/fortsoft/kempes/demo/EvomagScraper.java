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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fortsoft.kempes.WebScraper;
import ro.fortsoft.kempes.event.EventBusFactory;
import ro.fortsoft.kempes.ecommerce.event.CategoryUrlEvent;

import java.util.Collections;
import java.util.Set;

/**
 * This class read siteMapUrl, extract all category urls and post these urls on the event bus.
 *
 * @author Decebal Suiu
 */
public class EvomagScraper implements WebScraper {

    private static final Logger log = LoggerFactory.getLogger(EvomagScraper.class);

    private static final String siteMapUrl = "http://www.evomag.ro/Pagini/Harta-Site";

    private String[] categoryNames;

    public EvomagScraper(String... categoryNames) {
        this.categoryNames = categoryNames;
    }

    @Override
    public void init() {
        log.debug("Init evomag scraper");
        EventBusFactory.getEventBus().register(new EvomagEventHandler());
    }

    @Override
    public void start() {
        log.debug("Start evomag scraper");
        Set<String> urls = parseCategoryUrls();
        log.debug("Found {} category urls", urls.size());

        onCategoryUrls(urls);
    }

    protected void onCategoryUrls(Set<String> urls) {
        for (String url : urls) {
            EventBusFactory.getEventBus().post(new CategoryUrlEvent(url));
        }
    }

    protected Set<String> parseCategoryUrls() {
        log.debug("Retrieving category urls from '{}'", siteMapUrl);
        EvomagCategoryUrlsExtractor extractor = new EvomagCategoryUrlsExtractor(categoryNames);
        try {
            return extractor.extract(siteMapUrl);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptySet();
        }
    }

}
