package com.stonempv.crawler;

import com.stonempv.crawler.web.CrawlerRequest;
import com.stonempv.crawler.web.CrawlerResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CrawlerServiceMainTests {

		@Autowired
		private TestRestTemplate restTemplate;


		@Test
		public void checkValidPost() {
			ResponseEntity<CrawlerResponse> responseEntity =
							restTemplate.postForEntity("/api/crawler",
											new CrawlerRequest("localhost:8080"),
											CrawlerResponse.class);

			assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
			assertThat(responseEntity.getBody().getUri()).isEqualTo("localhost:8080");

		}

		@Test
		public void checkInvalidPost() {
			ResponseEntity<CrawlerResponse> responseEntity =
						restTemplate.postForEntity("/api/crawler",
										new CrawlerRequest(null),
										CrawlerResponse.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

	}

}
