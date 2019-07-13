package com.tensquare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.SnowflakeIdWorker;

/**
 * @EnableEurekaClient
 * 标注为springCloud Eureka客户端
 */
@EnableEurekaClient
@SpringBootApplication
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }

    @Bean
    public SnowflakeIdWorker idWorker(){
        return new SnowflakeIdWorker(1, 1);
    }
}