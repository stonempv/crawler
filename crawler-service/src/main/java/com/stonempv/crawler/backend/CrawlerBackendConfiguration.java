package com.stonempv.crawler.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by mi332208 on 13/02/2017.
 */

@Configuration
public class CrawlerBackendConfiguration {

  @Bean
  public CrawlerService crawlerService() { return new CrawlerService();}
}
