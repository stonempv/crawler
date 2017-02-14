package com.stonempv.crawler.backend;

import com.stonempv.crawler.web.CrawlerResponse;

import java.net.URL;

/**
 * Created by mi332208 on 13/02/2017.
 */
public class CrawlerService {
  private URL url;
  private String results;

  protected CrawlerService()  {}

  public CrawlerResponse doCrawl(URL url){
    this.url = url;
    return new CrawlerResponse(url, crawl());
  }

  private String crawl() {
    return "Hello World";
  }
}
