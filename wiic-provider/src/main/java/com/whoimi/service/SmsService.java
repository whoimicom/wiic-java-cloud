package com.whoimi.service;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Component
//@FeignClient(value = "huij-sms",path = "/huij-sms")
public interface SmsService {

    @PostMapping(value = "/dahan/send")
    ResponseEntity<Object> send(@Valid @RequestBody SendSmsReq req);

}