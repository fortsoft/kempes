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
package ro.fortsoft.kempes.entity;

import ro.fortsoft.kempes.WebPage;
import ro.fortsoft.kempes.WebPageFetcher;

/**
 * @author Decebal Suiu
 */
public abstract class EntityExtractor<T> {

    protected EntityMapper<T> mapper;
    protected WebPageFetcher fetcher;

    public EntityExtractor(WebPageFetcher fetcher, EntityMapper<T> mapper) {
        this.mapper = mapper;
        this.fetcher = fetcher;
    }

    public WebPageFetcher getFetcher() {
        return fetcher;
    }

    public EntityMapper<T> getMapper() {
        return mapper;
    }

    public T extract(String url) throws Exception {
        WebPage page = getFetcher().fetch(url);
        T object = createEntity();
        getMapper().map(page, object);

        return object;
    }

    public abstract T createEntity() throws Exception;

}
