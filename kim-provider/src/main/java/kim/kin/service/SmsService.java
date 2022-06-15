package kim.kin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Component
@FeignClient("huij-sms")
public interface SmsService {


    @PostMapping(value = "/dahan/send")
    ResponseEntity<Object> send(@Valid @RequestBody SendSmsReq req);


}