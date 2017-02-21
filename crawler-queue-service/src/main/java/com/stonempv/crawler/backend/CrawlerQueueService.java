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

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by mi332208 on 13/02/2017.
 */

public class CrawlerQueueService {

  public enum Queue_State {
    NOT_FOUND,
    IN_QUEUE,
    PROCESSED
  }

  private int id = 0;

  private ArrayList<Integer> queueArray;
  private HashMap<Integer, String> resultsMap;

  private static final Logger LOGGER = LoggerFactory
          .getLogger(CrawlerQueueService.class);

  @Autowired
  private KafkaTemplate<Integer, String> kafkaTemplate;

  protected CrawlerQueueService()  {
    queueArray = new ArrayList<Integer>();
    resultsMap = new HashMap<Integer, String>();
  }

  public String addCrawlTask(URL url){
    Integer id = getId();

    ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send("Crawler.new", id,  url.toString());

    try {
      SendResult<Integer, String> result = future.get(10, TimeUnit.SECONDS);

      LOGGER.info("sent message='{}' with offset={}",
              url.toString(),
              result.getRecordMetadata().offset());

      addTasktoQueue(id);

      return id.toString();

    } catch (InterruptedException | ExecutionException | TimeoutException e){

      LOGGER.error("unable to send message='{}'",
              url.toString(), e);

      return null;
    }

  }

  public void addTasktoQueue(Integer id){
    this.queueArray.add(id);
  }

  public Queue_State getTaskState(Integer taskId){
    if(!this.queueArray.contains(taskId)){
      return Queue_State.NOT_FOUND;
    } else if (this.resultsMap.containsKey(taskId)) {
      return Queue_State.PROCESSED;
    } else {
      return Queue_State.IN_QUEUE;
    }
  }

  @KafkaListener(topics = "Crawler.processed")
  public void doCrawl(@Payload String message,
                      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key) {
    LOGGER.info("received message='{}'", message);
    this.resultsMap.put(key, message);
  }


  public URI getProcessedUri(Integer taskId){
    try {
      return new URI("/api/crawler/" + this.resultsMap.get(taskId));
    } catch (URISyntaxException e) {
      return null;
    }
  }

  public URI getQueueUri(Integer taskId){
    try {
      return new URI("/api/queue/" + taskId);
    } catch (URISyntaxException e) {
      return null;
    }
  }

  private int getId(){
    this.id++;
    return this.id;
  }

}
