package com.stonempv.crawler.backend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;

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

}
