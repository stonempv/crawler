package com.stonempv.crawler.backend;

import com.stonempv.crawler.web.CrawlerResponse;

import java.net.URI;

/**
 * Created by mi332208 on 13/02/2017.
 */
public class CrawlerService {

  private String results;

  public CrawlerService() {}

  public CrawlerResponse doCrawl(String uri){
    return new CrawlerResponse(uri, "Hello Word");
  }
}
