package com.stonempv.crawler.common.crawler;


import java.net.URL;

/**
 * Created by mi332208 on 13/02/2017.
 */
public class CrawlerResponse {

  private String url;
  private Object results;

  protected CrawlerResponse() {}

  public CrawlerResponse(String url, Object results) {
    this.url = url;
    this.results = results;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String uri) {
    this.url = uri;
  }

  public Object getResults() {
    return results;
  }

  public void setResults(Object results) {
    this.results = results;
  }
}
