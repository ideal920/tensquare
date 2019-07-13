package com.tensquare.friend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import util.JwtUtils;
import util.SnowflakeIdWorker;

@SpringBootApplication
//开启Eureka注解
@EnableEurekaClient
//开启feign注解
@EnableDiscoveryClient
@EnableFeignClients
public class FriendApplication {
    public static void main(String[] args) {
        SpringApplication.run(FriendApplication.class, args);
    }
    @Bean
    public SnowflakeIdWorker idWorkker(){
        return new SnowflakeIdWorker(1, 1);
    }
    @Bean
    public JwtUtils jwtUtil(){
        return new util.JwtUtils();
    }
}
