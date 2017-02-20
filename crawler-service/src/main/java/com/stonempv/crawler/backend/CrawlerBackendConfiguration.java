package com.stonempv.crawler.backend;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mi332208 on 13/02/2017.
 */

@Configuration
@EnableKafka
@EnableMongoRepositories
public class CrawlerBackendConfiguration {

  @Value("${kafka.bootstrap.servers}")
  private String bootstrapServers;

  @Bean
  public Map consumerConfigs() {
    Map props = new HashMap<>();
    // list of host:port pairs used for establishing the initial connections
    // to the Kakfa cluster
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            bootstrapServers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            IntegerDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class);
    // consumer groups allow a pool of processes to divide the work of
    // consuming and processing records
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "Crawler");

    return props;
  }

  @Bean
  public ConsumerFactory consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs());
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());

    return factory;
  }
  @Bean
  public CrawlerService crawlerService() { return new CrawlerService();}
}
