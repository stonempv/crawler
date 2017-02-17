package com.stonempv.crawler.queue.backend;


import java.net.URL;

/**
 * Created by mi332208 on 13/02/2017.
 */
public class CrawlerQueueService {

  protected CrawlerQueueService()  {}

  public String addCrawlTask(URL url){
    //TODO add crawl task and send back id
    return null;
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

}
