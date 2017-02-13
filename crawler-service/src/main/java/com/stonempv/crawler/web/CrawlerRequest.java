package com.stonempv.crawler.web;

import org.hibernate.validator.constraints.URL;

import javax.validation.Constraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 * Created by mi332208 on 13/02/2017.
 */
public class CrawlerRequest {

  @NotNull
  private String uri;

  protected CrawlerRequest() {}

  public CrawlerRequest(String uri){
    this.uri = uri;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }
}
