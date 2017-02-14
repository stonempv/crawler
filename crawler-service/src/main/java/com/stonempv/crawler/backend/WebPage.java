package com.stonempv.crawler.backend;

import java.util.List;

/**
 * Created by mi332208 on 14/02/2017.
 */
public class WebPage {

  private List<WebPage> internalPages;
  private List<String> externalPages;

  public WebPage(){

  }

  public List<WebPage> getInternalPages() {
    return internalPages;
  }

  public void addInternalPages(WebPage internalPage) {
    this.internalPages.add(internalPage);
  }

  public List<String> getExternalPages() {
    return externalPages;
  }

  public void addExternalPage(String externalPage) {
    this.externalPages.add(externalPage);
  }
}
