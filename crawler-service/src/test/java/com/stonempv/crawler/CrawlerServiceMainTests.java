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

/* IMPORTANT: Integration Test will only succeed when application is running */


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CrawlerServiceMainTests {

  @Autowired
  private TestRestTemplate restTemplate;


  @Test
  public void checkValidPost() {
    ResponseEntity<CrawlerResponse> responseEntity =
            restTemplate.postForEntity("/api/crawler",
                    new CrawlerRequest("http://www.thisdoesntresolve.com/"),
                    CrawlerResponse.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);


  }

  @Test
  public void checkInvalidPost() {
    ResponseEntity<CrawlerResponse> responseEntity =
            restTemplate.postForEntity("/api/crawler",
                    new CrawlerRequest(null),
										CrawlerResponse.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

	}

  @Test
  public void crawlCheckBadTarget() {
    ResponseEntity<CrawlerResponse> responseEntity =
            restTemplate.postForEntity("/api/crawler",
                    new CrawlerRequest("...sdfa.fds"),
                    CrawlerResponse.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

  }

	@Test
  public void crawlGetsValidResults() {
    ResponseEntity<CrawlerResponse> responseEntity =
            restTemplate.postForEntity("/api/crawler",
                    new CrawlerRequest("http://localhost:8080/"),
                    CrawlerResponse.class);

    assertThat(responseEntity.getBody().getUrl().toString()).isEqualTo("http://localhost:8080/");
    String response = responseEntity.getBody().getResults().toString();
    assertThat(response).isEqualTo("{/={title=Test Page for Crawler, location=http://localhost:8080/, internalPages={http://localhost:8080/pages/page2.html=Other link to Page 2, http://localhost:8080/page1.html=Link to Page 1}, externalLinks={http://www.google.com=Google it}}, /page1.html={title=Test Page for Crawler, location=http://localhost:8080/page1.html, internalPages={http://localhost:8080/pages/subpages/subpage2.html=Link to Sub Page 2, http://localhost:8080/pages/subpages/subpage1.html=Link to Sub Page 1, http://localhost:8080/=Link to Home}, externalLinks={}}, /pages/page2.html={title=Test Page for Crawler, location=http://localhost:8080/pages/page2.html, internalPages={http://localhost:8080/pages/subpages/subpage3.html=Link to Sub Page 3, http://localhost:8080/pages/subpages/subpage2.html=Link to Sub Page 2, http://localhost:8080/=Link to Home}, externalLinks={}}, /pages/subpages/subpage1.html={title=Test Page for Crawler, location=http://localhost:8080/pages/subpages/subpage1.html, internalPages={http://localhost:8080/=Link to Home}, externalLinks={}}, /pages/subpages/subpage2.html={title=Test Page for Crawler, location=http://localhost:8080/pages/subpages/subpage2.html, internalPages={http://localhost:8080/=Link to Home}, externalLinks={}}, /pages/subpages/subpage3.html={title=Test Page for Crawler, location=http://localhost:8080/pages/subpages/subpage3.html, internalPages={http://localhost:8080/=Link to Home}, externalLinks={}}}");
  }
}
