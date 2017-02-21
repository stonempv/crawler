package com.stonempv.crawler.backend;

import org.springframework.data.annotation.Id;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mi332208 on 20/02/2017.
 */
public class WebPage {

  @Id private String title;
  private String href;
  private String host;

  private HashMap<String, String> internalPages;
  private HashMap<String, String> externalLinks;
  private HashMap<String, String> otherResources;
  private ArrayList<String> badLinks;

  public WebPage(String title, String href, String host){
    this.title = title;
    this.href = href;
    this.host = host;

    internalPages = new HashMap<String, String>();
    externalLinks = new HashMap<String, String>();
    otherResources = new HashMap<String, String>();
    badLinks = new ArrayList<String>();
  }

  public void addChildPage(String title, String href) {
    if (isLocalURL(href)) {
      internalPages.put(href, title);
    } else {
      externalLinks.put(href, title);
    }
  }

  public void addOtherResource(String title, String href){
    otherResources.put(href, title);
  }

  public HashMap<String, String> getInternalPages(){
    return internalPages;
  }
  public HashMap<String, String> getExternalLinks(){
    return externalLinks;
  }
  public HashMap<String, String> getOtherResources() { return otherResources; }
  public ArrayList<String> getBadLinks() { return badLinks; }


  private boolean isLocalURL(String url){
    try {
      URL absurl = new URL(url);
      return stripWWW(absurl.getHost()).equalsIgnoreCase(stripWWW(this.host));
    } catch (Exception e) {
      return false;
    }
  }

  private String stripWWW(String host){
    if(host.startsWith("www")){
      return host.substring("www".length()+1);
    } else {
      return host;
    }
  }

}
