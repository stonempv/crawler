package com.stonempv.crawler.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mi332208 on 13/02/2017.
 */
public class CrawlerService {

  @Autowired
  private SiteMapRepository repository;

  @Autowired
  private KafkaTemplate<Integer, String> kafkaTemplate;

  private static final Logger LOGGER = LoggerFactory
          .getLogger(CrawlerService.class);

  protected CrawlerService()  {}

  @KafkaListener(topics = "Crawler.new")
  public void doCrawl(@Payload String message,
                      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key) {
    LOGGER.info("received message='{}'", message);
    LOGGER.info("received key='{}'", key);


    try {
      URL url = new URL(message);
      HttpCrawler crawler = new HttpCrawler(url);
      crawler.doCrawl(url);
      SiteMap siteMap = crawler.getSiteMap();

      repository.save(siteMap);

      ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send("Crawler.processed", key, crawler.getSiteMap().getId());

      future.addCallback(
              new ListenableFutureCallback<SendResult<Integer, String>>() {

                @Override
                public void onSuccess(
                        SendResult<Integer, String> result) {
                  LOGGER.info("sent message='{}' with offset={}",
                          message,
                          result.getRecordMetadata().offset());
                }

                @Override
                public void onFailure(Throwable ex) {
                  LOGGER.error("unable to send message='{}'",
                          message, ex);
                }
              });

    } catch (MalformedURLException e){
      LOGGER.error("Could no create crawl request for {}", message);
    }
  }

  public SiteMap returnResults(String crawlId) {
    return repository.findById(crawlId);
  }

}
