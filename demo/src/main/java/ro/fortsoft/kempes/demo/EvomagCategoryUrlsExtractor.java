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

import java.util.*;

/**
 * @author Decebal Suiu
 */
public class EvomagCategoryUrlsExtractor extends EntityExtractor<Set<String>> {

    private static final Logger log = LoggerFactory.getLogger(EvomagCategoryUrlsExtractor.class);

    public EvomagCategoryUrlsExtractor(String... categoryNames) {
//        super(new DefaultWebPageFetcher(), new CategoryUrlsMapper(categoryNames));
        super(new HttpClientWebPageFetcher(), new CategoryUrlsMapper(categoryNames));
    }

    @Override
    public Set<String> createEntity() throws Exception {
        return new LinkedHashSet<String>();
    }

    public static class CategoryUrlsMapper implements EntityMapper<Set<String>> {

        private String[] categoryNames;

        public CategoryUrlsMapper(String... categoryNames) {
            this.categoryNames = categoryNames;
        }

        @Override
        public void map(WebPage page, Set<String> entity) throws Exception {
            // create category filter
            List<String> categoryFilter = new ArrayList<String>();
            for (String category : categoryNames) {
                categoryFilter.add(category.toLowerCase());
            }

            // parse for category urls
            Document document = page.getDocument();
            Iterator<Element> elements = document.select("li.sitemap_li > a").iterator();
            while (elements.hasNext()) {
                Element element = elements.next();
                String name = element.text().toLowerCase();
                String url = element.attr("href");
                url = UrlUtils.ensureAbsoluteUrl(page.getUrl(), url);
                log.debug("Found category url '{}'", url);
                if (categoryFilter.isEmpty()) {
                    entity.add(url);
                    log.debug("Add category url '{}'", url);
                } else if (categoryFilter.contains(name)) {
                    entity.add(url);
                    log.debug("Add category url '{}'", url);
                    break;
                }
            }
        }

    }

}
