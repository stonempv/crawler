package com.stonempv.crawler.backend;

import com.stonempv.crawler.common.crawler.CrawlerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.net.URL;

/**
 * Created by mi332208 on 13/02/2017.
 */
public class CrawlerService {

  private static final Logger LOGGER = LoggerFactory
          .getLogger(CrawlerService.class);

  protected CrawlerService()  {}

  @KafkaListener(topics = "Crawler.new")
  public void receiveMessage(String message) {
    LOGGER.info("received message='{}'", message);
  }

  public CrawlerResponse doCrawl(URL url){
    HttpCrawler crawler = new HttpCrawler();
    return new CrawlerResponse(url, crawler.doCrawl(url));
  }



}
