package com.stonempv.crawler.backend;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mi332208 on 14/02/2017.
 */
public class WebPage {

  private List<String> crawledPages;

  private String title;
  private String location;
  private String host;

  private HashMap<String, String> internalPages;
  private HashMap<String, String> externalLinks;

  public WebPage(Document doc, String host) {
    this.title = doc.title();
    this.location = doc.location();
    this.host = host;

    internalPages = new HashMap<String, String>();
    externalLinks = new HashMap<String, String>();

    Elements elements = doc.body().select("a[href]");
    for (Element element : elements) {
      String href = element.attr("abs:href");
      if (!href.equals("")) {
        if (isLocalURL(href)) {
          internalPages.put(element.attr("abs:href"), element.ownText());
        } else {
          externalLinks.put(element.attr("abs:href"), element.ownText());
        }
      }
    }
  }

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public HashMap<String, String> getInternalPages() {
    return internalPages;
  }

  public void setInternalPages(HashMap<String, String> internalPages) {
    this.internalPages = internalPages;
  }

  public HashMap<String, String> getExternalLinks() {
    return externalLinks;
  }

  public void setExternalLinks(HashMap<String, String> externalLinks) {
    this.externalLinks = externalLinks;
  }
}
