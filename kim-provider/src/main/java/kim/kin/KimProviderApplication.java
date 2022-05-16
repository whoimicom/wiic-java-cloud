package kim.kin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class KimProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(KimProviderApplication.class, args);
    }
}