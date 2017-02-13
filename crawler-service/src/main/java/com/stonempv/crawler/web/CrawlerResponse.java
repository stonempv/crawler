package com.stonempv.crawler.web;

import java.net.URI;

/**
 * Created by mi332208 on 13/02/2017.
 */
public class CrawlerResponse {

  private String uri;
  private String results;

  protected CrawlerResponse() {}

  public CrawlerResponse(String uri, String results) {
    this.uri = uri;
    this.results = results;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getResults() {
    return results;
  }

  public void setResults(String results) {
    this.results = results;
  }
}
