package com.stonempv.crawler;

import com.stonempv.crawler.web.CrawlerQueueWebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by mi332208 on 17/02/2017.
 */
@Configuration
@Import({CrawlerQueueWebConfiguration.class})
@SpringBootApplication
public class CrawlerQueueServiceMain {

  public static void main(String[] args) {
    SpringApplication.run(CrawlerQueueServiceMain.class, args);
  }
}
