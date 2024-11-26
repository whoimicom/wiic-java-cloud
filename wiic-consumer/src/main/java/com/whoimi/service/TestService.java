package com.whoimi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
//@FeignClient("wiic-provider")
public interface TestService {
    @GetMapping(value = "/wiic-provider/pathVariable/{id}")
    public String pathVariable(@PathVariable("id") Integer id);

}