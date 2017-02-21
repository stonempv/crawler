package com.stonempv.crawler.queue.web;

import com.stonempv.crawler.common.crawler.CrawlerCreateResponse;
import com.stonempv.crawler.common.crawler.CrawlerRequest;
import com.stonempv.crawler.common.crawler.QueueResponse;
import com.stonempv.crawler.queue.backend.CrawlerQueueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
;
import static org.mockito.BDDMockito.*;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by mi332208 on 17/02/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerQueueControllerTest {

  @MockBean
  private CrawlerQueueService queueService;

  @Autowired
  private CrawlerQueueController queueController;


  @Test
  public void testAddCrawlValidUrlWithHttp() throws Exception {
    CrawlerRequest request = new CrawlerRequest("http://www.google.com");
    given(this.queueService.addCrawlTask(any(URL.class))).willReturn("1234");

    ResponseEntity<?> response = queueController.addCrawlTask(request);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

  }

  @Test
  public void testAddCrawlValidUrlWithHttps() {
    CrawlerRequest request = new CrawlerRequest("https://www.google.com");
    given(this.queueService.addCrawlTask(any(URL.class))).willReturn("1234");

    ResponseEntity<?> response = queueController.addCrawlTask(request);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

  }

  @Test
  public void testAddCrawlValidUrlWithoutHttp() {
    CrawlerRequest request = new CrawlerRequest("www.google.com");
    given(this.queueService.addCrawlTask(any(URL.class))).willReturn("1234");

    ResponseEntity<?> response = queueController.addCrawlTask(request);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

  }

  @Test
  public void testAddCrawlInvalidUrl() {
    CrawlerRequest request = new CrawlerRequest("bad://www.google.com");

    ResponseEntity<?> response = queueController.addCrawlTask(request);
    verify(queueService, never()).addCrawlTask(any(URL.class));
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

  }

  @Test
  public void testAddCrawlNotAccepted() {
    CrawlerRequest request = new CrawlerRequest("www.google.com");

    given(this.queueService.addCrawlTask(any(URL.class))).willReturn(null);
    ResponseEntity<?> response = queueController.addCrawlTask(request);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);

  }

  @Test
  public void testAddCrawlAccepted(){
    CrawlerRequest request = new CrawlerRequest("www.google.com");
    CrawlerCreateResponse expectedResponse = new CrawlerCreateResponse("/api/queue/1234");

    given(this.queueService.addCrawlTask(any(URL.class))).willReturn("1234");
    ResponseEntity<?> response = queueController.addCrawlTask(request);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    assertThat(response.getBody()).isEqualToComparingFieldByField(expectedResponse);

  }

  @Test
  public void testCrawlTaskIsInQueue(){
    QueueResponse expectedResponse = new QueueResponse("pending", "/api/queue/1234");

    given(this.queueService.isCrawlTaskInQueue(any(String.class))).willReturn(true);
    ResponseEntity<?> response = queueController.isCrawlTaskInQueue("1234");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualToComparingFieldByField(expectedResponse);

  }

  @Test
  public void testCrawlTaskNotInQueue(){
    QueueResponse expectedResponse = new QueueResponse("processed", "/api/crawler/2345");

    given(this.queueService.isCrawlTaskInQueue(any(String.class))).willReturn(false);
    ResponseEntity<?> response = queueController.isCrawlTaskInQueue("2345");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.GONE);
    assertThat(response.getBody()).isEqualToComparingFieldByField(expectedResponse);

  }



}
