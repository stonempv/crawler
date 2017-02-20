package com.stonempv.crawler.queue.web;

import com.stonempv.crawler.queue.backend.CrawlerQueueBackendConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by mi332208 on 13/02/2017.
 */

@Configuration
@Import({CrawlerQueueBackendConfiguration.class})
@ComponentScan
public class CrawlerQueueWebConfiguration {

}
