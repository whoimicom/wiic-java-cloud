package kim.kin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Component
@FeignClient("huij-sms")
public interface SmsService {


    @PostMapping(value = "/dahan/send")
    ResponseEntity<Object> send(@Valid @RequestBody SendSmsReq req);


}