package com.tensquare.qa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import util.JwtUtils;
import util.SnowflakeIdWorker;

/**
 * @EnableFeignClients 远程服务调用客户端
 * @EnableEurekaClient 标注为springCloud Eureka客户端
 */
@EnableFeignClients
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class QaApplication {

	public static void main(String[] args) {
		SpringApplication.run(QaApplication.class, args);
	}

	@Bean
	public SnowflakeIdWorker idWorker(){
		return new SnowflakeIdWorker(1, 1);
	}

	@Bean
	public JwtUtils getJwtutils(){
		return new JwtUtils();
	}
}

//Feign注解
