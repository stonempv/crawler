package com.stonempv.crawler.web;

import com.stonempv.crawler.backend.SiteMap;
import com.stonempv.crawler.common.crawler.CrawlerRequest;
import com.stonempv.crawler.backend.CrawlerService;
import com.stonempv.crawler.common.crawler.CrawlerResponse;
import com.stonempv.crawler.common.crawler.QueueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.net.MalformedURLException;


/**
 * Created by mi332208 on 13/02/2017.
 */
@RestController
@RequestMapping("/api/crawler")
public class CrawlerController {

  private CrawlerService crawlerService;

  @Autowired
  public CrawlerController(CrawlerService crawlerService) {
    this.crawlerService = crawlerService;
  }

  @RequestMapping(value = "{crawlId}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<?> getCrawlResults(@PathVariable("crawlId") String crawlId) {
    SiteMap map = crawlerService.returnResults(crawlId);
    if (map != null) {
      return ResponseEntity.ok().body(new CrawlerResponse(map.getId(), map.getPages()));
    } else {
      return new ResponseEntity<>("Request Not Found", HttpStatus.NOT_FOUND);
    }
  }

}
