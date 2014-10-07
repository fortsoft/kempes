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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Decebal Suiu
 */
public abstract class AbstractWebPageFetcher implements WebPageFetcher {

    private static final Logger log = LoggerFactory.getLogger(AbstractWebPageFetcher.class);

    private String userAgent;

    @Override
    public WebPage fetch(String url) throws Exception {
        if (!new URI(url).isAbsolute()) {
            log.warn("The url {} must be absolute", url);
        }

        WebPage page = new WebPage(url);
        InputStream inputStream = null;
        try {
            if (System.getProperty("kempes.fetcher.diskCache") != null) {
                File file = getFile(url);
                if (file != null) {
                    if (!file.exists()) {
                        download(url, file);
                    }

                    inputStream = new FileInputStream(file);
                } else {
                    inputStream = getContentStream(url);
                }
            } else {
                inputStream = getContentStream(url);
            }

            Document document = createDocument(inputStream);
            page.setDocument(document);
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) { }
            }
        }
        return page;
    }

    /**
     * See http://en.wikipedia.org/wiki/User_agent for more details.
     */
    public String getUserAgent() {
        if (userAgent == null) {
            // return default value
            return "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52";
        }

        return userAgent;
    }

    /**
     * See http://en.wikipedia.org/wiki/User_agent for more details.
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    protected File getFile(String url) {
        if (url.endsWith("/")) {
            url = url.concat("index.html");
//        } else  if (!url.endsWith(".html")) {
//            url = url.concat(".html");
        }

        Path filePath;
        try {
            String baseDomain = UrlUtils.getBaseDomain(url);
            String[] tmp = baseDomain.split("\\.");
            String urlPath = new URL(url).getFile();
            filePath = Paths.get("sites", tmp[1], tmp[0], urlPath);
        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
            return null;
        }

        Path parentPath = filePath.getParent();
        File folder = parentPath.toFile();
        if (!folder.exists() || !folder.isDirectory()) {
            boolean created = folder.mkdirs();
            if (created) {
                log.debug("Created folder '{}'", folder);
            } else {
                log.error("Cannot create folder '{}'", folder);
                return null;
            }
        }

        return filePath.toFile();
    }

    protected void download(String url, File file) throws IOException {
        // fetch page content
        log.debug("Downloading '{}' to '{}'", url, file);
        try (
            InputStream inputStream = getContentStream(url);
            FileOutputStream outputStream = new FileOutputStream(file)
        ) {
            int nextByte;
            while ((nextByte = inputStream.read()) != -1) {
                outputStream.write(nextByte);
            }
        }
        log.debug("Downloaded '{}' to '{}'", url, file);
    }

    protected abstract InputStream getContentStream(String url) throws IOException;

    protected Document createDocument(InputStream stream) throws IOException {
        return Jsoup.parse(stream, "UTF8", "");
    }

}
