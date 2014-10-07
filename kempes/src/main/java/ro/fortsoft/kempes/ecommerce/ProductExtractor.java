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
package ro.fortsoft.kempes.ecommerce;

import ro.fortsoft.kempes.WebPageFetcher;
import ro.fortsoft.kempes.entity.EntityExtractor;

/**
 * @author Decebal Suiu
 */
public class ProductExtractor extends EntityExtractor<Product> {

    public ProductExtractor(WebPageFetcher fetcher, ProductMapper mapper) {
        super(fetcher, mapper);
    }

    @Override
    public Product extract(String url) throws Exception {
        Product product = super.extract(url);
        product.setUrl(url);

        return product;
    }

    @Override
    public Product createEntity() throws Exception {
        return new Product();
    }

}
