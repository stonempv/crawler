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
public class WebPageCrawl {


  public static WebPage crawlWebPage(Document doc, String host) {

    WebPage webPage = new WebPage(doc.title(), doc.location(), host);

    Elements elements = doc.body().select("a[href]");
    for (Element element : elements) {
      String href = element.attr("abs:href");
      String title = element.ownText();

      if (href != ""){
        webPage.addChildPage(title, href);
      }
    }

    Elements imgs = doc.body().select("img");
    for (Element img : imgs) {
      String href =  img.attr("src");
      String title = img.attr("alt");

      webPage.addOtherResource(title, href);
    }

    return webPage;

  }


}
