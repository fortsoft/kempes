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

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fortsoft.kempes.HttpClientWebPageFetcher;
import ro.fortsoft.kempes.UrlUtils;
import ro.fortsoft.kempes.WebPage;
import ro.fortsoft.kempes.entity.EntityExtractor;
import ro.fortsoft.kempes.entity.EntityMapper;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Extract all product urls from a category page.
 *
 * @author Decebal Suiu
 */
public class EvomagProductUrlsExtractor extends EntityExtractor<Set<String>> {

    private static final Logger log = LoggerFactory.getLogger(EvomagProductUrlsExtractor.class);

    public EvomagProductUrlsExtractor() {
//        super(new DefaultWebPageFetcher(), new ProductUrlsMapper());
        super(new HttpClientWebPageFetcher(), new ProductUrlsMapper());
    }

    @Override
    public Set<String> createEntity() throws Exception {
        return new LinkedHashSet<String>();
    }

    public static class ProductUrlsMapper implements EntityMapper<Set<String>> {

        @Override
        public void map(WebPage page, Set<String> entity) throws Exception {
            Document document = page.getDocument();
            Iterator<Element> elements = document.select("div.prod_list_det.tabel-view h2 > a").iterator();
            while (elements.hasNext()) {
                Element element = elements.next();
                String url = element.attr("href");
                url = UrlUtils.ensureAbsoluteUrl(page.getUrl(), url);
                entity.add(url);
                log.debug("Add product url '{}'", url);
            }
        }

    }

}
