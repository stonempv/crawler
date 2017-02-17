package com.stonempv.crawler.queue.web;

import com.stonempv.crawler.common.crawler.CrawlerRequest;
import com.stonempv.crawler.queue.backend.CrawlerQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    Object response;

    try {
      String taskId = queueService.addCrawlTask(new URL(request.getUrl()));

      if (taskId != null) {
        //TODO add success response
        response = null;
      } else {
        //TODO add other failed response
        response = null;
      }

    } catch (MalformedURLException e){
      //TODO add malformed URLException response
      response =  null;
    }

    return new ResponseEntity<>("Create Task Not Yet Implemented", HttpStatus.NOT_IMPLEMENTED);
  }

  @RequestMapping(path = "/queue/{jobId}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<?> getCrawlTask(){
    //TODO implement get queued task
    return new ResponseEntity<>("Get Task Not Yet Implemented", HttpStatus.NOT_IMPLEMENTED);
  }

  @RequestMapping(path = "/queue/{jobId}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<?> deleteCrawlTask(){
    //TODO implement delete queue task
    return new ResponseEntity<>("Delete Task Not Yet Implemented", HttpStatus.NOT_IMPLEMENTED);
  }

}
