package kim.kin.service;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Component
//@FeignClient(value = "huij-sms",path = "/huij-sms")
public interface SmsService {


    @PostMapping(value = "/dahan/send")
    ResponseEntity<Object> send(@Valid @RequestBody SendSmsReq req);


}