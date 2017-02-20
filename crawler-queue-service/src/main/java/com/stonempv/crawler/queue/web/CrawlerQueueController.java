package com.stonempv.crawler.queue.web;

import com.stonempv.crawler.common.crawler.CrawlerCreateResponse;
import com.stonempv.crawler.common.crawler.CrawlerRequest;
import com.stonempv.crawler.common.crawler.QueueResponse;
import com.stonempv.crawler.queue.backend.CrawlerQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by mi332208 on 13/02/2017.
 */
@RestController
@RequestMapping({"/api"})
public class CrawlerQueueController {

  private CrawlerQueueService queueService;

  @Autowired
  public CrawlerQueueController(CrawlerQueueService queueService) {
    this.queueService = queueService;
  }

  @RequestMapping(value = "/crawler", method = RequestMethod.POST)
  public @ResponseBody
  ResponseEntity<?> addCrawlTask(@Validated @RequestBody CrawlerRequest request) {

    try {
      String taskId = queueService.addCrawlTask(new URL(decorateURL(request.getUrl())));

      if (taskId != null) {
        return ResponseEntity.accepted().body(new CrawlerCreateResponse(queueTaskUrl(taskId)));
      } else {
        return new ResponseEntity<>("Your Crawl Request was not accepted", HttpStatus.NOT_ACCEPTABLE);
      }

    } catch (MalformedURLException e){
      return new ResponseEntity<>("URL Passed is not a valid URL", HttpStatus.BAD_REQUEST);
    }

  }

  @RequestMapping(path = "/queue/{taskId}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<?> isCrawlTaskInQueue(@PathVariable("taskId") String taskId) {
    if (queueService.isCrawlTaskInQueue(taskId)) {
      return ResponseEntity.ok().body(new QueueResponse("pending", queueTaskUrl(taskId)));
    } else {
      return new ResponseEntity<>(new QueueResponse("processed", crawlerTaskUrl(taskId)), HttpStatus.GONE);
    }
  }

  @RequestMapping(path = "/queue/{taskId}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<?> deleteCrawlTask(@PathVariable("taskId") String taskId){
    if (queueService.deleteCrawlTask(taskId)){
      return ResponseEntity.ok().body("");
    } else {
      return new ResponseEntity("", HttpStatus.GONE);
    }
  }


  public static String decorateURL(String uri){
    String url = uri;
    if (!url.toLowerCase().matches("^\\w+://.*")) {
      url = "http://" + url;
    }
    return url;
  }

  public static String queueTaskUrl(String taskId){
    return "/api/queue/" + taskId;
  }

  public static String crawlerTaskUrl(String taskId){
    return "/api/crawler/" + taskId;
  }
}
