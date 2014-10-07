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
import ro.fortsoft.kempes.HttpClientWebPageFetcher;
import ro.fortsoft.kempes.WebPage;
import ro.fortsoft.kempes.ecommerce.Product;
import ro.fortsoft.kempes.ecommerce.ProductExtractor;
import ro.fortsoft.kempes.ecommerce.ProductMapper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Fetch a product info from a web page.
 *
 * @author Decebal Suiu
 */
public class EvomagProductExtractor extends ProductExtractor {

    public EvomagProductExtractor() {
//        super(new DefaultWebPageFetcher(), new EvomagProductMapper());
        super(new HttpClientWebPageFetcher(), new EvomagProductMapper());
    }

    public static class EvomagProductMapper extends ProductMapper {

        @Override
        public void map(WebPage page, Product object) throws Exception {
            Document document = page.getDocument();

            // parse for title
            Element element = document.select("div h1").first();
            object.setName(element.text());

            // parse for price
            element = document.select("div.pret_ron").first();
            String tmp = element.ownText();
            tmp = tmp.split(" ")[0];
            object.setPrice(parsePrice(tmp));
        }

        protected Double parsePrice(String price) throws ParseException {
            DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(new Locale("ro", "RO"));
//        format.setParseBigDecimal(true);

            return format.parse(price).doubleValue();
        }

    }

}

