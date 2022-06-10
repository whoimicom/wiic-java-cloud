package kim.kin.rest;

import kim.kin.service.SendSmsReq;
import kim.kin.service.SmsService;
import kim.kin.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            System.out.println(str);
//            return restTemplate.getForObject("http://service-provider/echo/" + str, String.class);
//            return restTemplate.getForObject("http://example/nacos/" + str, String.class);
            return testService.getPayment(Integer.valueOf(str));
        }

        @PostMapping(value = "/huij-sms")
        public ResponseEntity<Object> sms() {
            SendSmsReq sendSmsReq = new SendSmsReq();
            sendSmsReq.setSmsSign("惠众惠");
            sendSmsReq.setSmsType("TZ");
            sendSmsReq.setContent("您的验证码为：407886，有效期90秒，如非本人操作，请联系客服023-8600-3581。");
            sendSmsReq.setPhones("18580588800");
            sendSmsReq.setSourceChannel("fireway");
            sendSmsReq.setTempId("__");
            ResponseEntity<Object> send = smsService.send(sendSmsReq);
            System.out.println("end");
            System.out.println(send);
            return send;
        }
    }