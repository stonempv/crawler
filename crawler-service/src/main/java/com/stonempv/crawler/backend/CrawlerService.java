package com.stonempv.crawler.backend;

import com.stonempv.crawler.web.CrawlerResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

/**
 * Created by mi332208 on 13/02/2017.
 */
public class CrawlerService {
  private URL url;
  private String results;

  protected CrawlerService()  {}

  public CrawlerResponse doCrawl(URL url){
    this.url = url;
    return new CrawlerResponse(url, crawl());
  }

  private Object crawl() {

    Object map;
    try {
      Document document = Jsoup.connect(url.toString()).get();

      map = new String("Hello World");
    } catch (IOException e){
      System.out.println("bad");
      map = null;
    }
    return map;
  }
}
