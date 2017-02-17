package com.stonempv.crawler.web;

import com.stonempv.crawler.common.crawler.CrawlerRequest;
import com.stonempv.crawler.backend.CrawlerService;
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

  @RequestMapping(method = RequestMethod.POST)
  public @ResponseBody Object crawl(@Validated @RequestBody CrawlerRequest request) {

    Object response;

    try {
      response = crawlerService.doCrawl(new URL(request.getUrl()));

    } catch (MalformedURLException e){
      response =  new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    return response;
  }


}
