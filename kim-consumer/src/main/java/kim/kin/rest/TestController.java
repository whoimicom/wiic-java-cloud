package kim.kin.rest;

import kim.kin.service.SendSmsReq;
import kim.kin.service.SmsService;
import kim.kin.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);
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
        log.info(str);
//            return restTemplate.getForObject("http://service-provider/echo/" + str, String.class);
//            return restTemplate.getForObject("http://example/nacos/" + str, String.class);
        return testService.pathVariable(Integer.valueOf(str));
    }

    @PostMapping(value = "/huij-sms")
    public ResponseEntity<Object> sms(@Valid @RequestBody SendSmsReq req) {
        ResponseEntity<Object> send = smsService.send(req);
        log.info(send.toString());
        return send;
    }
}