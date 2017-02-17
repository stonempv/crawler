package com.stonempv.crawler.common.crawler;

import javax.validation.constraints.NotNull;

/**
 * Created by mi332208 on 13/02/2017.
 */
public class CrawlerRequest {

  @NotNull
  private String url;

  protected CrawlerRequest() {}

  public CrawlerRequest(String url){
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
