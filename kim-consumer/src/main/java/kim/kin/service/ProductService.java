package kim.kin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="springcloud-product-provider")
public interface ProductService {

    /**
     * 查询
     * @return
     */
    @GetMapping("product/provider/get/info")
    public Object getServiceInfo();

    /**
     * 查询
     * @param id
     * @return
     */
    @GetMapping("product/provider/get/{id}")
    public Object selectById(@PathVariable("id") Long id);
}