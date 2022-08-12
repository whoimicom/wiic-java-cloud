package kim.kin;

import kim.kin.config.AccessLogFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class KimConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KimConsumerApplication.class, args);
    }
//    @Bean
//    public AccessLogFilter accessLogFilter(){
//        return new AccessLogFilter();
//    }
}