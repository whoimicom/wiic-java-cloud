package kim.kin.rest;

import kim.kin.service.SendSmsReq;
import kim.kin.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRest {
    private static final Logger log = LoggerFactory.getLogger(TestRest.class);
    @Value("${server.port}")
    private String serverPort;
    private final SmsService smsService;

    @Autowired
    public TestRest(SmsService smsService) {
        this.smsService = smsService;
    }

    @GetMapping(value = "/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id) {
        log.info(id.toString());
        SendSmsReq sendSmsReq = new SendSmsReq();
        sendSmsReq.setSmsSign("惠众惠");
        sendSmsReq.setSmsType("TZ");
        sendSmsReq.setContent("您的验证码为：407886，有效期90秒，如非本人操作，请联系客服023-8600-3581。");
        sendSmsReq.setPhones("18580588800");
        sendSmsReq.setSourceChannel("fireway");
        sendSmsReq.setTempId("__");
        ResponseEntity<Object> send = smsService.send(sendSmsReq);
        log.info(send.toString());
        return send.toString();
//        return "服务名：spring-cloud-alibaba-provider<br /> 端口号： " + serverPort + "<br /> 传入的参数：" + id;
    }
}