package com.stonempv.crawler.web;

import com.stonempv.crawler.backend.CrawlerQueueService;
import com.stonempv.crawler.common.crawler.CrawlerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

;

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
    given(this.queueService.getQueueUri(new Integer(1234))).willReturn(new URI("/api/queue/1234"));

    ResponseEntity<?> response = queueController.addCrawlTask(request);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

  }

  @Test
  public void testAddCrawlValidUrlWithHttps() throws URISyntaxException{
    CrawlerRequest request = new CrawlerRequest("https://www.google.com");
    given(this.queueService.addCrawlTask(any(URL.class))).willReturn("1234");
    given(this.queueService.getQueueUri(new Integer(1234))).willReturn(new URI("/api/queue/1234"));

    ResponseEntity<?> response = queueController.addCrawlTask(request);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

  }

  @Test
  public void testAddCrawlValidUrlWithoutHttp() throws URISyntaxException{
    CrawlerRequest request = new CrawlerRequest("www.google.com");
    given(this.queueService.addCrawlTask(any(URL.class))).willReturn("1234");
    given(this.queueService.getQueueUri(new Integer(1234))).willReturn(new URI("/api/queue/1234"));

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
  public void testAddCrawlAccepted() throws URISyntaxException{
    CrawlerRequest request = new CrawlerRequest("www.google.com");

    ResponseEntity expectedResponse = ResponseEntity.accepted().header("Location","/api/queue/1234").body("");

    given(this.queueService.addCrawlTask(any(URL.class))).willReturn("1234");
    given(this.queueService.getQueueUri(any(Integer.class))).willReturn(new URI("/api/queue/1234"));

    ResponseEntity<?> response = queueController.addCrawlTask(request);
    assertThat(response).isEqualToComparingFieldByField(expectedResponse);

  }

  @Test
  public void testCrawlTaskIsInQueue(){

    ResponseEntity expectedResponse = ResponseEntity.ok().body("");

    given(this.queueService.getTaskState(any(Integer.class))).willReturn(CrawlerQueueService.Queue_State.IN_QUEUE);

    ResponseEntity<?> response = queueController.isCrawlTaskInQueue("1234");

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response).isEqualToComparingFieldByField(expectedResponse);

  }

  @Test
  public void testCrawlTaskIsProcessed() throws URISyntaxException{
    ResponseEntity expectedResponse = ResponseEntity.status(HttpStatus.GONE).header("Location","/api/crawler/www_test_com").body("");

    given(this.queueService.getTaskState(any(Integer.class))).willReturn(CrawlerQueueService.Queue_State.PROCESSED);
    given(this.queueService.getProcessedUri(any(Integer.class))).willReturn(new URI("/api/crawler/www_test_com"));

    ResponseEntity<?> response = queueController.isCrawlTaskInQueue("2345");

    assertThat(response).isEqualToComparingFieldByField(expectedResponse);

  }

  @Test
  public void testCrawlTaskNotFound() {
    ResponseEntity expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("");

    given(this.queueService.getTaskState(any(Integer.class))).willReturn(CrawlerQueueService.Queue_State.NOT_FOUND);

    ResponseEntity<?> response = queueController.isCrawlTaskInQueue("2345");

    assertThat(response).isEqualToComparingFieldByField(expectedResponse);

  }



}
