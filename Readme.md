# WebCrawler REST Microservice

## Getting Started
This is a SpringBoot REST API client for the crawling of a target website with the response in JSON.
It utilises SpringBoot, JSOUP and Gradle so it should be relatively straight forward to setup and run.

It also has an embedded website that is loaded at http://localhost:8080/ when starting the application.

This can be used to test the endpoint (or a real address like http://www.wiprodigital.com/ can be).

This website is used for the integration tests so modifying this site may affect the success of the integration tests and conversly the integration tests require the server to be running to be 100% successful.

### How to build
//Clone the git repository
git clone https://github.com/stonempv/crawler.git

//navigate to the application root directory and run the application
./gradlew clean bootRun

//Use a tool like curl or postman to test the endpoint
curl -X POST -H "Content-Type: application/json" -d '{"url":"http://www.wiprodigital.com/"}' http://localhost:8080/api/crawler/

// Wala!


## Improvements
Apart from the areas where the code is shit because I'm rusty as f00k the following improvements should be made.
1. The project is structured as a microservice architecture which is a bit over the top for one service, to extend this out for more services Docker should be implemented as SpringBoot will only run one service at a time (as far as I can see)
2. Could be improved by adding Swagger over the top but this seem reasonably trivial and I wanted feedback on the important stuff.
3. I tested largely against the Integration tests but I think more unit test could be important
4. I used recursion which always scares me a little bit, but I struggled to think of a better approach.

Thanks for your help

Mike xx

