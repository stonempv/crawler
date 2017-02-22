package com.stonempv.crawler.backend;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mi332208 on 14/02/2017.
 */
public class HttpCrawler {


  private SiteMap siteMap;

  private static final Logger LOGGER = LoggerFactory
          .getLogger(HttpCrawler.class);

  public HttpCrawler(URL url) {
    this.siteMap = new SiteMap(url.toString());
  }


  public void doCrawl(URL url) {
    try {
      Document document = Jsoup.connect(url.toString()).get();

      WebPage webPage = WebPageCrawl.crawlWebPage(document, url.getHost());
      siteMap.addWebPage(url, webPage);
      LOGGER.info("Added new page: {}", document.title());

      Iterator it = webPage.getInternalPages().entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry pair = (Map.Entry) it.next();
        URL childUrl = new URL(pair.getKey().toString());
        if (!this.siteMap.hasPage(childUrl)) {
          doCrawl(childUrl);
        }
      }
    } catch (IOException e) {
      siteMap.addBadLink(url);
    }
  }

  public SiteMap getSiteMap(){
    return siteMap;
  }

}
