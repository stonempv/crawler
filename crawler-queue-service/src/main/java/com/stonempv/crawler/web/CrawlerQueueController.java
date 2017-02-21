package com.stonempv.crawler.web;

import com.stonempv.crawler.common.crawler.CrawlerRequest;
import com.stonempv.crawler.backend.CrawlerQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URI;
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
        URI uri = queueService.getQueueUri(new Integer(taskId));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uri);
        return ResponseEntity.accepted().headers(responseHeaders).body("");

      } else {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("");
      }

    } catch (MalformedURLException e){
      return ResponseEntity.badRequest().body("MalformedURLException");
    }

  }

  @RequestMapping(path = "/queue/{taskId}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<?> isCrawlTaskInQueue(@PathVariable("taskId") String taskId) {
    CrawlerQueueService.Queue_State state = queueService.getTaskState(new Integer(taskId));

    switch (state) {
      case NOT_FOUND:
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
      case PROCESSED:
        URI uri = queueService.getProcessedUri(new Integer(taskId));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uri);
        return ResponseEntity.status(HttpStatus.GONE).headers(responseHeaders).body("");
      default:
        return ResponseEntity.ok().body("");
    }
  }


  public static String decorateURL(String uri){
    String url = uri;
    if (!url.toLowerCase().matches("^\\w+://.*")) {
      url = "http://" + url;
    }
    return url;
  }


}
