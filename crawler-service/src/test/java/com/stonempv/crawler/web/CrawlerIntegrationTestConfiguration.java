package com.stonempv.crawler.web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by mi332208 on 13/02/2017.
 */

@Configuration
@Import({CrawlerWebConfiguration.class})
@EnableAutoConfiguration
public class CrawlerIntegrationTestConfiguration {
}
