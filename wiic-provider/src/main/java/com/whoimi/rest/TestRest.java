package com.whoimi.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestRest {
    private static final Logger log = LoggerFactory.getLogger(TestRest.class);
    @Value("${server.port}")
    private String serverPort;
//    private final SmsService smsService;

//    @Autowired
//    public TestRest(SmsService smsService) {
//        this.smsService = smsService;
//    }

    @GetMapping(value = "/pathVariable/{id}")
    public String pathVariable(@PathVariable("id") Integer id) {
        String a=" 端口号： " + serverPort + "<br /> 传入的参数：" + id;
        log.info(a);
        return a;
    }

//    @GetMapping(value = "/huij-sms")
//    public String send(@Valid @RequestBody SendSmsReq req) {
//        ResponseEntity<Object> send = smsService.send(req);
//        log.info(send.toString());
//        return send.toString();
//    }
}