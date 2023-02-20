package sj.springclould.order.controller;

import entities.CommonResult;
import entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

    //使用注册到eureka的服务地址，就是在webui中application这一列的值
    private String PAYMENT_HTTP = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/create")
    public CommonResult create(Payment payment) {
        System.out.println(payment);
        CommonResult forObject = restTemplate.postForObject(PAYMENT_HTTP + "/payment/create",
                payment, //post数据域
                CommonResult.class //接口返回的json数据对应的javaBean
        );
        return forObject;
    }

    @GetMapping("/consumer/get/{id}")
    public CommonResult getPaymentById(@PathVariable() Long id) {
        CommonResult forObject = restTemplate.getForObject(PAYMENT_HTTP + "/payment/get/" + id,
                CommonResult.class
        );
        System.out.println(forObject);
        return forObject;
    }
}
