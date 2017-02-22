package com.stonempv.crawler.backend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mi332208 on 20/02/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerQueueServiceTests {

  @Autowired
  private CrawlerQueueService queueService;

  @Test
  public void testAddCrawlValidUrlWithHttp() throws Exception {
    String response = queueService.addCrawlTask(new URL("http://www.google.com"));
    assertThat(response).isEqualTo("1");

  }

  @Test
  public void testQueryQueueInQueue(){
    queueService.addTasktoQueue(new Integer(1));
    CrawlerQueueService.Queue_State state = queueService.getTaskState(new Integer(1));
    assertThat(state).isEqualTo(CrawlerQueueService.Queue_State.IN_QUEUE);
  }

  @Test
  public void testQueryQueueNotInQueue(){;
    CrawlerQueueService.Queue_State state = queueService.getTaskState(new Integer(10));
    assertThat(state).isEqualTo(CrawlerQueueService.Queue_State.NOT_FOUND);
  }

  @Test
  public void testQueryQueueProcessed() throws URISyntaxException {;
    queueService.addTasktoQueue(new Integer(5));
    queueService.doCrawl("www_wiprodigital_com", new Integer(5));
    CrawlerQueueService.Queue_State state = queueService.getTaskState(new Integer(5));
    URI uri = queueService.getProcessedUri(new Integer(5));

    assertThat(state).isEqualTo(CrawlerQueueService.Queue_State.PROCESSED);
    assertThat(uri).isEqualTo(new URI("/api/crawler/www_wiprodigital_com"));
  }

}
