package com.stonempv.crawler;

import com.stonempv.crawler.web.CrawlerWebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CrawlerWebConfiguration.class})
@EnableAutoConfiguration
@ComponentScan
public class CrawlerServiceMain {

	public static void main(String[] args) {
		SpringApplication.run(CrawlerServiceMain.class, args);
	}
}
