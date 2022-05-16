package kim.kin.rest;

import kim.kin.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableDiscoveryClient
public class NacosConsumerApplication {



    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerApplication.class, args);
    }

    @RestController
    public class TestController {

//        private final RestTemplate restTemplate;
        private final TestService testService;

//        @Autowired
//        public TestController(RestTemplate restTemplate, TestService testService) {
//            this.restTemplate = restTemplate;
//            this.testService = testService;
//        }
        @Autowired
        public TestController( TestService testService) {
            this.testService = testService;
        }

        @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
        public String echo(@PathVariable String str) {
//            return restTemplate.getForObject("http://service-provider/echo/" + str, String.class);
//            return restTemplate.getForObject("http://example/nacos/" + str, String.class);
            return testService.getPayment(Integer.valueOf(str));
        }
    }
}