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

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Decebal Suiu
 */
public class HttpClientWebPageFetcher extends AbstractWebPageFetcher {

    protected HttpClient createHttpClient() throws IOException {
        String proxyHost = System.getProperty("http.proxyHost");
        HttpHost proxy = null;
        if (proxyHost != null) {
            int proxyPort = Integer.parseInt(System.getProperty("http.proxyPort"));
            proxy = new HttpHost(proxyHost, proxyPort);
        }

        String userAgent = getUserAgent();
        HttpClientBuilder builder = HttpClientBuilder.create().setUserAgent(userAgent);
        if (proxy != null) {
            builder.setProxy(proxy);
        }

        return builder.build();
    }

    protected InputStream getContentStream(String url) throws IOException {
        HttpClient client = createHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        return response.getEntity().getContent();
    }

}
