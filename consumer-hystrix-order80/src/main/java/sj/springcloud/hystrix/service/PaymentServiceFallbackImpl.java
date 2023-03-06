package sj.springcloud.hystrix.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentServiceFallbackImpl implements PaymentService{
    @Override
    public String ok() {
        return "PaymentServiceFallbackImpl:ok";
    }

    @Override
    public String timeout() {
        return "PaymentServiceFallbackImpl:timeout";
    }
}
