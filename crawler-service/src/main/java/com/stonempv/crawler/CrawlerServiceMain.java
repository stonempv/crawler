package com.stonempv.crawler;

import com.stonempv.crawler.web.CrawlerWebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CrawlerWebConfiguration.class})
@SpringBootApplication
public class CrawlerServiceMain {

	public static void main(String[] args) {
		SpringApplication.run(CrawlerServiceMain.class, args);
	}
}
