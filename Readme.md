# WebCrawler REST Microservice

## Getting Started
This is a SpringBoot REST API client for the crawling of a target website with the response in JSON.
It utilises SpringBoot, JSOUP, Gradle, Docker, Kafka and Mongodb so it should be relatively straight forward to setup and run.

It also has an embedded website that is loaded at http://localhost:8080/ when starting the application.

This can be used to test the endpoint (or a real address like http://www.wiprodigital.com/ can be).

This website is used for the integration tests so modifying this site may affect the success of the integration tests and conversly the integration tests require the server to be running to be 100% successful.

### How to build
Clone the git repository
```
git clone https://github.com/stonempv/crawler.git
```

Navigate to the application root directory and run the application
```
./gradlew assemble
sudo sh ./set-env.sh
docker-compose build
docker-compose up -d --build
```

Use a tool like curl or postman to test the endpoint
```
curl -X POST -H "Content-Type: application/json" -d '{"url":"http://www.wiprodigital.com/"}' http://localhost:8080/api/crawler/
```

Wala!


## Improvements based on Code Review
1. ~~Handle exception URL's in a more useful way, perhaps create a new section in the JSON to report on them?~~
2. ~~Modify the architecture to return a response immediately with unique id and queue the job~~
3. ~~Provide new endpoint for querying the status of a crawl~~
4. ~~Provide a new endpoint to retrieve the results of a query~~
5. ~~Split the responsibility for doing the crawl and presenting the results~~
6. Look at how multithreading could be used to do the crawl
7. ~~Add unit tests~~
8. ~~Git Ignore .DS_Store~~
9. ~~Add swagger documentation~~


Thanks for your help

Mike xx

