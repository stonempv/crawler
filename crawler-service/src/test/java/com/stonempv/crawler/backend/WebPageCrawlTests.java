package com.stonempv.crawler.backend;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mi332208 on 22/02/2017.
 */
@SpringBootTest
public class WebPageCrawlTests {

  @Autowired
  private WebPageCrawl webPageCrawl;

  @Test
  public void testEmptyWebPage(){
    String html = "<html>"
            + "<head>"
            + "<title>This is title</title>"
            + "</head>"
            + "<body><p>Parsed HTML into a doc.</p></body>"
            + "</html>";

    Document doc = Jsoup.parse(html, "http://www.wiprodigital.com");


    WebPage expectedWebPage = new WebPage("This is title", "http://www.wiprodigital.com", "");

    WebPage webPage = webPageCrawl.crawlWebPage(doc, "");

    assertThat(webPage).isEqualToComparingFieldByField(expectedWebPage);
  }

  @Test
  public void testOneExetrnalLinkPage(){
    String html = "<html>"
            + "<head>"
            + "<title>This is title</title>"
            + "</head>"
            + "<body><a href='http://www.google.com'>Google it</a></body>"
            + "</html>";

    Document doc = Jsoup.parse(html, "http://www.wiprodigital.com");


    WebPage expectedWebPage = new WebPage("This is title", "http://www.wiprodigital.com", "");
    expectedWebPage.addChildPage("Google it", "http://www.google.com");

    WebPage webPage = webPageCrawl.crawlWebPage(doc, "");

    assertThat(webPage).isEqualToComparingFieldByField(expectedWebPage);
  }

  @Test
  public void testOneInternalLinkPage(){
    String html = "<html>"
            + "<head>"
            + "<title>This is title</title>"
            + "</head>"
            + "<body><a href='/test'>Test</a></body>"
            + "</html>";

    Document doc = Jsoup.parse(html, "http://www.wiprodigital.com");


    WebPage expectedWebPage = new WebPage("This is title", "http://www.wiprodigital.com", "8080");
    expectedWebPage.addChildPage("Test", "http://www.wiprodigital.com/test");

    WebPage webPage = webPageCrawl.crawlWebPage(doc, "8080");

    assertThat(webPage).isEqualToComparingFieldByField(expectedWebPage);
  }


  @Test
  public void testOneOtherResourcePage(){
    String html = "<html>"
            + "<head>"
            + "<title>This is title</title>"
            + "</head>"
            + "<body><img alt='Test' src='http://imageurl_1.com'></body>"
            + "</html>";

    Document doc = Jsoup.parse(html, "http://www.wiprodigital.com");


    WebPage expectedWebPage = new WebPage("This is title", "http://www.wiprodigital.com", "");
    expectedWebPage.addOtherResource("Test", "http://imageurl_1.com");

    WebPage webPage = webPageCrawl.crawlWebPage(doc, "");

    assertThat(webPage).isEqualToComparingFieldByField(expectedWebPage);
  }

  @Test
  public void testMultiResourcePage(){
    String html = "<html>"
            + "<head>"
            + "<title>This is title</title>"
            + "</head>"
            + "<body>"
            + "<img alt='Img 1' src='http://imageurl_1.com'>"
            + "<a href='/internal_1'>Internal 1</a>"
            + "<a href='http://wiprodigital.com/internal_2'>Internal 2</a>"
            + "<a href='http://www.external.com/1'>External 1</a>"
            + "<img alt='Img 2' src='http://imageurl_2.com'>"
            + "<img alt='Img 3' src='http://imageurl_3.com'>"
            + "<a href='http://www.external.com/2'>External 2</a>"
            + "<a href='http://www.externalother.com/3'>External 3</a>"
            + "<a href='/test/internal_3'>Internal 3</a>"
            + "</body>"
            + "</html>";

    Document doc = Jsoup.parse(html, "http://www.wiprodigital.com");


    WebPage expectedWebPage = new WebPage("This is title", "http://www.wiprodigital.com", "80");
    expectedWebPage.addOtherResource("Img 1", "http://imageurl_1.com");
    expectedWebPage.addOtherResource("Img 2", "http://imageurl_2.com");
    expectedWebPage.addOtherResource("Img 3", "http://imageurl_3.com");
    expectedWebPage.addChildPage("Internal 1", "http://www.wiprodigital.com/internal_1");
    expectedWebPage.addChildPage("Internal 2", "http://wiprodigital.com/internal_2");
    expectedWebPage.addChildPage("Internal 3", "http://www.wiprodigital.com/test/internal_3");
    expectedWebPage.addChildPage("External 1", "http://www.external.com/1");
    expectedWebPage.addChildPage("External 2", "http://www.external.com/2");
    expectedWebPage.addChildPage("External 3", "http://www.externalother.com/3");

    WebPage webPage = webPageCrawl.crawlWebPage(doc, "80");

    assertThat(webPage).isEqualToComparingFieldByField(expectedWebPage);
  }

}
