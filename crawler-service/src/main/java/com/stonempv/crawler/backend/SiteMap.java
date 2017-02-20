package com.stonempv.crawler.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

import java.net.URL;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by mi332208 on 20/02/2017.
 */
public class SiteMap {

  @Id
  private String id;
  private SortedMap<String, WebPage> pages;

  public SiteMap(String id) {
    this.id = id;
    pages = new TreeMap<>();
  }

  public void addWebPage(URL url, WebPage page) {
    pages.put(addTrailingSlash(url.getPath()), page);
  }

  public boolean hasPage(URL url) {
    return pages.containsKey(addTrailingSlash(url.getPath()));
  }

  private String addTrailingSlash(String url){
    if(url.endsWith("/")){
      return url;
    } else {
      return url + "/";
    }
  }

}