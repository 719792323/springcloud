package sj.springcloud.openfegin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sj.springcloud.openfegin.service.PaymentFeignService;

import javax.annotation.Resource;

@RestController
public class Controller {

    @Resource
    PaymentFeignService payment;

    @RequestMapping(value = "/feign/order")
    public String order() {
        return payment.payment();
    }

    @RequestMapping(value = "/feign/timeout")
    public String timeout() {
        return payment.timeout();
    }

}
