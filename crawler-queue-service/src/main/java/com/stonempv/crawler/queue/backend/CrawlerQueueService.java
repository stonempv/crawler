package com.stonempv.crawler.queue.backend;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by mi332208 on 13/02/2017.
 */
public class CrawlerQueueService {

  private int id = 0;

  private static final Logger LOGGER = LoggerFactory
          .getLogger(CrawlerQueueService.class);

  @Autowired
  private KafkaTemplate<Integer, String> kafkaTemplate;

  protected CrawlerQueueService()  {}

  public String addCrawlTask(URL url){
    Integer id = getId();

    ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send("Crawler.new", id,  url.toString());

    try {
      SendResult<Integer, String> result = future.get(10, TimeUnit.SECONDS);

      LOGGER.info("sent message='{}' with offset={}",
              url.toString(),
              result.getRecordMetadata().offset());

      return id.toString();

    } catch (InterruptedException | ExecutionException | TimeoutException e){

      LOGGER.error("unable to send message='{}'",
              url.toString(), e);

      return null;
    }

  }

  public boolean isCrawlTaskInQueue(String taskId){
    //TODO implement poll Task
    //Options should be either InProgress or a 410 message
    return false;
  }

  public boolean deleteCrawlTask(String taskId){
    //TODO implement delete task
    return false;
  }

  private int getId(){
    this.id++;
    System.out.println(this.id);
    return this.id;
  }

}
