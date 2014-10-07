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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fortsoft.kempes.RandomDelay;
import ro.fortsoft.kempes.ecommerce.Product;
import ro.fortsoft.kempes.ecommerce.event.CategoryUrlEvent;
import ro.fortsoft.kempes.event.EventBusFactory;
import ro.fortsoft.kempes.ecommerce.event.ProductUrlEvent;
import ro.fortsoft.kempes.event.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Decebal Suiu
 */
public class EvomagEventHandler {

    private static final Logger log = LoggerFactory.getLogger(EvomagEventHandler.class);

    private static final String CSV_FILE = "products.csv";

    private CSVPrinter csvPrinter;

    @Subscribe
    public void onCategoryUrl(CategoryUrlEvent event) {
        RandomDelay.delay(3, 5);
        String categoryUrl = event.getUrl();
        Set<String> urls = parseProductUrls(categoryUrl);
        log.debug("Found {} product urls for category url '{}'", urls.size(), categoryUrl);
        for (String url : urls) {
            EventBusFactory.getEventBus().post(new ProductUrlEvent(url));
        }
    }

    @Subscribe
    public void onProductUrl(ProductUrlEvent event) {
        RandomDelay.delay(3, 5);
        String productUrl = event.getUrl();
        Product product = parseProduct(productUrl);
        log.debug("Extracted '{}' from '{}'", product, productUrl);

        // save in csv, database or do something else
        log.debug("Write '{}' to '{}'", product, CSV_FILE);
        try {
            writeToCsv(product);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private Set<String> parseProductUrls(String categoryUrl) {
        log.debug("Retrieving product urls for category url '{}'", categoryUrl);
        EvomagProductUrlsExtractor extractor = new EvomagProductUrlsExtractor();
        try {
            return extractor.extract(categoryUrl);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptySet();
        }
    }

    private Product parseProduct(String productUrl) {
        log.debug("Retrieving product from url '{}'", productUrl);
        EvomagProductExtractor extractor = new EvomagProductExtractor();
        try {
            return extractor.extract(productUrl);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private void writeToCsv(Product product) throws IOException {
        // TODO improve
        if (csvPrinter == null) {
            CSVFormat csvFormat = CSVFormat.RFC4180.withHeader().withDelimiter(',');
            csvPrinter = new CSVPrinter(new FileWriter(CSV_FILE), csvFormat.withDelimiter('#'));
            csvPrinter.printRecord("Name", "Price");
        }

        List<String> data = new ArrayList<String>();
        data.add(product.getName());
        data.add(String.valueOf(product.getPrice()));
        csvPrinter.printRecord(data);
        csvPrinter.flush();
        // TODO
//        csvPrinter.close();
    }

}
