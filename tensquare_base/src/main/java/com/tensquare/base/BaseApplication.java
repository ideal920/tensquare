package com.tensquare.base;

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
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class);
    }

    @Bean//将方法的返回值交给spring容器管理
    public SnowflakeIdWorker getIdWorker(){
        return new SnowflakeIdWorker(1,1);
    }

}
