package com.stonempv.crawler.backend;

import java.net.URI;

/**
 * Created by mi332208 on 14/02/2017.
 */
public interface Crawler {

  WebPage doCrawl(URI targetURI);

}
