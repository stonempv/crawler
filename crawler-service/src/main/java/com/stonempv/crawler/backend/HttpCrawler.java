package com.stonempv.crawler.backend;

import java.net.URI;

/**
 * Created by mi332208 on 14/02/2017.
 */
public class HttpCrawler implements Crawler{

  public WebPage doCrawl(URI uri){
    return new WebPage();
  }

}
