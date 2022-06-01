package kim.kin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("kim-provider")
public interface TestService {
    @GetMapping(value = "/nacos/{id}")
     String getPayment(@PathVariable("id") Integer id) ;


}