package kim.kin.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRest {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id) {
        return "服务名：spring-cloud-alibaba-provider<br /> 端口号： " + serverPort + "<br /> 传入的参数：" + id;
    }
}