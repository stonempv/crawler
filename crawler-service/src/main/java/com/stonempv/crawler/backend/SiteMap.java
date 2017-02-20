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
    this.id = convertId(id);
    pages = new TreeMap<>();
  }

  public void addWebPage(URL url, WebPage page) {
    pages.put(addTrailingSlash(url.getPath()), page);
  }

  public boolean hasPage(URL url) {
    return pages.containsKey(addTrailingSlash(url.getPath()));
  }

  public String getId(){
    return this.id;
  }

  public SortedMap<String, WebPage> getPages(){
    return this.pages;
  }

  private String addTrailingSlash(String url){
    if(url.endsWith("/")){
      return url;
    } else {
      return url + "/";
    }
  }

  private String convertId(String url){
    String newId;
    if(url.startsWith("http://")){
      newId = url.substring(7, url.length());
    } else {
      newId = url;
    }
    if(newId.endsWith("/")) {
      newId = newId.substring(0, newId.length()-1);
    }
    newId = newId.replaceAll("\\.","_");
    newId = newId.replaceAll("/","_");
    return newId;

  }

}