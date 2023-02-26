package sj.springcloud.openfegin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

//注意openfeign自带ribbon，所以修改轮询策略的方式就是在ribbon模块介绍的方式
@Component
@FeignClient(value = "cloud-provider-payment") //要调用的微服务名称
public interface PaymentFeignService {

    @GetMapping(value = "/consul/payment")
    String payment();

    @GetMapping(value = "/consul/timeout")
    String timeout();
}
