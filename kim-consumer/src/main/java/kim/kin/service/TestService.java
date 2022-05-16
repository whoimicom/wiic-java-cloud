package kim.kin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("example")
public interface TestService {
    @GetMapping(value = "/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id) ;


}