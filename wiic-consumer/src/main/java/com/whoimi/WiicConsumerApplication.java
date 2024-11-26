package com.whoimi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class WiicConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WiicConsumerApplication.class, args);
    }
//    @Bean
//    public AccessLogFilter accessLogFilter(){
//        return new AccessLogFilter();
//    }
}