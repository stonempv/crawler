package com.stonempv.crawler.common.crawler;


import java.net.URL;

/**
 * Created by mi332208 on 13/02/2017.
 */
public class QueueResponse {

  private String status;
  private String url;


  protected QueueResponse() {}

  public QueueResponse(String status, String url) {
    this.url = url;
    this.status = status;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String uri) {
    this.url = uri;
  }

  public String getRtatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
