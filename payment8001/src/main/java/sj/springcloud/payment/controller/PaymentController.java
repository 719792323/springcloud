package sj.springcloud.payment.controller;


import entities.CommonResult;
import entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import sj.springcloud.payment.service.PaymentService;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private Integer port;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        System.out.println(payment);
        int result = paymentService.create(payment);
        log.info("create:{},result:{},port:{}", payment, result, port);
        if (result > 0) {
            return new CommonResult(200, "create success,port:" + port, payment);
        } else {
            return new CommonResult(505, "create fail,port:" + port);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable() Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("getPaymentById:{},result:{},port:{}", id, payment, port);
        if (payment != null) {
            return new CommonResult(200, "get success,port:" + port, payment);
        } else {
            return new CommonResult(505, "get fail,port:" + port);
        }
    }
}
