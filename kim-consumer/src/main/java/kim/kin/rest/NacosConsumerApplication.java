package kim.kin.rest;

import kim.kin.service.SendSmsReq;
import kim.kin.service.SmsService;
import kim.kin.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


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
        private final SmsService smsService;

//        @Autowired
//        public TestController(RestTemplate restTemplate, TestService testService) {
//            this.restTemplate = restTemplate;
//            this.testService = testService;
//        }
        @Autowired
        public TestController(TestService testService, SmsService smsService) {
            this.testService = testService;
            this.smsService = smsService;
        }

        @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
        public String echo(@PathVariable String str) {
//            return restTemplate.getForObject("http://service-provider/echo/" + str, String.class);
//            return restTemplate.getForObject("http://example/nacos/" + str, String.class);
            return testService.getPayment(Integer.valueOf(str));
        }

        @RequestMapping(value = "/huij-sms", method = RequestMethod.GET)
        public ResponseEntity sms() {
            SendSmsReq sendSmsReq = new SendSmsReq();
            sendSmsReq.setSmsSign("惠众惠");
            sendSmsReq.setSmsType("TZ");
            sendSmsReq.setContent("您的验证码为：407886，有效期90秒，如非本人操作，请联系客服023-8600-3581。");
            sendSmsReq.setPhones("18580588800");
            sendSmsReq.setSourceChannel("fireway");
            sendSmsReq.setTempId("__");
            ResponseEntity send = smsService.send(sendSmsReq);
            System.out.println("end");
            System.out.println(send);
            return send;
        }
    }
}