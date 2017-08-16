package org.terracotta.ehcache3diskpersistencedemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.cache.CacheManager;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableCaching
public class Ehcache3DiskPersistenceDemoApplication {

  private final static Logger LOGGER = LoggerFactory.getLogger(Ehcache3DiskPersistenceDemoApplication.class);

  @Autowired
  CacheManager cacheManager;

  public static void main(String[] args) {
    SpringApplication.run(Ehcache3DiskPersistenceDemoApplication.class, args);
  }

  @PostConstruct
  public void postContruct() {
    Cache<Object, Object> alerts = cacheManager.getCache("Alerts");
    alerts.iterator().forEachRemaining(keyValueEntry -> LOGGER.warn("we found this key : " + keyValueEntry.getKey()));

    LOGGER.info("Filling in the cache ...");
    IntStream.range(0, 500).asLongStream().forEach(value -> alerts.put(value, new String("value for " + value)));
    LOGGER.info("Done filling the cache");
  }

}
