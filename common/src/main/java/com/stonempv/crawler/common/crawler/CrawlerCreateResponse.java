package com.stonempv.crawler.common.crawler;


/**
 * Created by mi332208 on 13/02/2017.
 */
public class CrawlerCreateResponse {

  private String location;

  protected CrawlerCreateResponse() {}

  public CrawlerCreateResponse(String location) {
    this.location = location;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
