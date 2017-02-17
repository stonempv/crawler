package com.stonempv.crawler.apigateway.main;

import com.stonempv.crawler.apigateway.ApiGatewayServiceConfiguration;
import org.springframework.boot.SpringApplication;

/**
 * Created by Main on 19.01.2016.
 */
public class ApiGatewayServiceMain {
  public static void main(String[] args) {
    System.out.println("ARGS: " + args);
    SpringApplication.run(ApiGatewayServiceConfiguration.class, args);
  }
}
