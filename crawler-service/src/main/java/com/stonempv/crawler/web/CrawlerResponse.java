package com.stonempv.crawler.web;

import org.springframework.http.ResponseEntity;

import java.net.URL;

/**
 * Created by mi332208 on 13/02/2017.
 */
public class CrawlerResponse {

  private URL url;
  private String results;

  protected CrawlerResponse() {}

  public CrawlerResponse(URL url, String results) {
    this.url = url;
    this.results = results;
  }

  public URL getUrl() {
    return url;
  }

  public void setUrl(URL uri) {
    this.url = uri;
  }

  public String getResults() {
    return results;
  }

  public void setResults(String results) {
    this.results = results;
  }
}
