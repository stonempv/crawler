package com.stonempv.crawler.web;

import com.stonempv.crawler.backend.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
  public @ResponseBody CrawlerResponse crawl(@Validated @RequestBody CrawlerRequest request) {
    return crawlerService.doCrawl(request.getUri());
  }


}
