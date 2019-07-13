package com.tensquare.gathering;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.SnowflakeIdWorker;

/**
 * @EnableCaching 开启缓存支持
 * @EnableEurekaClient 标注为SpringCloud Eureka客户端
 */
@EnableEurekaClient
@SpringBootApplication
@EnableCaching
public class GatheringApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatheringApplication.class, args);
	}

	@Bean
	public SnowflakeIdWorker idWorkker(){
		return new SnowflakeIdWorker(1, 1);
	}
	
}
