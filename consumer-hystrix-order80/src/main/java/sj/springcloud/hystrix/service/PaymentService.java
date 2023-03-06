package sj.springcloud.hystrix.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient(value = "cloud-provider-hystrix-payment",
        fallback = PaymentServiceFallbackImpl.class)//指定调用接口时对应的服务降级类，该类必须实现本接口（PaymentService）
public interface PaymentService {
    @RequestMapping(value = "/hystrix/ok")
    String ok();

    @RequestMapping(value = "/hystrix/timeout")
    String timeout();
}
