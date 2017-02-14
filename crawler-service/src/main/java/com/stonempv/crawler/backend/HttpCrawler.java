package com.stonempv.crawler.backend;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by mi332208 on 14/02/2017.
 */
public class HttpCrawler {

  private ArrayList<String> crawledPages = new ArrayList();
  private SortedMap<String, WebPage> results = new TreeMap<String, WebPage>();

  public SortedMap<String, WebPage> doCrawl(URL url){

    if (!crawledPages.contains(url.getPath())) {
      try {

        Document document = Jsoup.connect(url.toString()).get();
        WebPage page = new WebPage(document, url.getHost());
        results.put(url.getPath(), page);
        crawledPages.add(url.getPath());

        Iterator it = page.getInternalPages().entrySet().iterator();
        while (it.hasNext()) {
          Map.Entry pair = (Map.Entry) it.next();
          URL childUrl = new URL(pair.getKey().toString());
          doCrawl(childUrl);
        }


      } catch (IOException e) {
        System.out.println(e);
      }
    }
    return results;
  }

}
