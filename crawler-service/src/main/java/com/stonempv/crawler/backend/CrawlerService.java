package com.stonempv.crawler.backend;

import com.stonempv.crawler.common.crawler.CrawlerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mi332208 on 13/02/2017.
 */
public class CrawlerService {

  @Autowired
  private SiteMapRepository repository;

  private static final Logger LOGGER = LoggerFactory
          .getLogger(CrawlerService.class);

  protected CrawlerService()  {}


  @KafkaListener(topics = "Crawler.new")
  public void doCrawl(String message){
    LOGGER.info("received message='{}'", message);
    try {
      URL url = new URL(message);
      HttpCrawler crawler = new HttpCrawler(url);
      crawler.doCrawl(url);
      SiteMap siteMap = crawler.getSiteMap();

      repository.save(siteMap);

    } catch (MalformedURLException e){
      LOGGER.error("Could no create crawl request for {}", message);
    }
  }

  public SiteMap returnResults(String crawlId) {
    return repository.findById(crawlId);
  }



}
