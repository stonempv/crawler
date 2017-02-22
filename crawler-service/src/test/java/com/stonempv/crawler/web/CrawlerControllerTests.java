package com.stonempv.crawler.web;

import com.stonempv.crawler.backend.CrawlerService;
import com.stonempv.crawler.backend.SiteMap;
import com.stonempv.crawler.backend.WebPage;
import com.stonempv.crawler.common.crawler.CrawlerResponse;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

/**
 * Created by mi332208 on 22/02/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerControllerTests {

  @MockBean
  private CrawlerService crawlerService;

  @Autowired
  private CrawlerController crawlerController;

  @Test
  public void testGetInvalidCrawl() {
    ResponseEntity expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("");

    given(crawlerService.returnResults(any(String.class))).willReturn(null);

    ResponseEntity<?> response = crawlerController.getCrawlResults("www_badrequest_com");

    assertThat(response).isEqualToComparingFieldByField(expectedResponse);

  }

  @Test
  public void testGetValidCrawl() {

    JSONObject json = new JSONObject();
    SiteMap siteMap = new SiteMap("www_wiprodigital_com");
    CrawlerResponse crawlerResponse = new CrawlerResponse("www_wiprodigital_com", json);
    ResponseEntity expectedResponse = ResponseEntity.status(HttpStatus.OK).body(crawlerResponse);


    given(crawlerService.returnResults(any(String.class))).willReturn(siteMap);

    ResponseEntity response = crawlerController.getCrawlResults("www_wiprodigital_com");

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualToComparingFieldByField(crawlerResponse);

  }
}
