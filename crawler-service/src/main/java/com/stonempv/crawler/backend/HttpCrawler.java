package com.stonempv.crawler.backend;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

  ExecutorService executorService =
    new ThreadPoolExecutor(
            5,
            5,
            1,
            TimeUnit.MINUTES,
            new ArrayBlockingQueue<Runnable>(5, true),
            new ThreadPoolExecutor.CallerRunsPolicy());



  public void doCrawl(URL url){
    try{
      Document document = Jsoup.connect(url.toString()).get();

      WebPage webPage = WebPageCrawl.crawlWebPage(document, url.getHost());
      siteMap.addWebPage(url, webPage);
      LOGGER.info("Added new page: {}", document.title());

      Iterator it = webPage.getInternalPages().entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry pair = (Map.Entry) it.next();
        URL childUrl = new URL(pair.getKey().toString());
        if (!this.siteMap.hasPage(childUrl)) {
          executorService.submit(new Runnable() {
            @Override
            public void run() {
                doCrawl(childUrl);
            }
          });
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
