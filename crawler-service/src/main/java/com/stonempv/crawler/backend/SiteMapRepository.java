package com.stonempv.crawler.backend;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by mi332208 on 20/02/2017.
 */
public interface SiteMapRepository extends MongoRepository<SiteMap, String> {

  public SiteMap findById(String id);

}
