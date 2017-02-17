package com.stonempv.crawler.backend;

import com.stonempv.crawler.common.crawler.CrawlerResponse;

import java.net.URL;

/**
 * Created by mi332208 on 13/02/2017.
 */
public class CrawlerService {

  protected CrawlerService()  {}

  public CrawlerResponse doCrawl(URL url){
    HttpCrawler crawler = new HttpCrawler();
    return new CrawlerResponse(url, crawler.doCrawl(url));
  }



}
