package sj.springcloud.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sj.springcloud.hystrix.service.PaymentService;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "error")//指定默认异常处理方法，如果标注了@HystrixCommand的方法，但是
//没有指定fallbackMethod，则会使用defaultFallback指定的方法，如果指定了，则还是用指定的方法处理
//没有标注的@HystrixCommand不会受任何影响
public class Controller {

    @Value("${server.port}")
    private Integer port;

    @Resource
    PaymentService paymentService;

    @RequestMapping(value = "/hystrix/ok")
    public String ok() {
        return paymentService.ok();
    }

    @HystrixCommand(fallbackMethod = "timeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")})
    @RequestMapping(value = "/hystrix/timeout")
    public String timeout() {
        return paymentService.timeout();
    }

    //测试在PaymentServiceFallbackImpl定义的服务降级方法
    @RequestMapping(value = "/hystrix/timeout2")
    public String timeout2() {
        return paymentService.timeout();
    }


    @RequestMapping(value = "/hystrix/error1")
    @HystrixCommand
    public String error1() {
        int i = 1 / 0;
        return "error1";
    }

    @RequestMapping(value = "/hystrix/error2")
    public String error2() {
        int i = 1 / 0;
        return "error2";
    }

    public String timeoutHandler() {
        return "paymentService.timeout():timeout";
    }

    public String error() {
        return "全局异常处理";
    }

    //服务熔断
    @RequestMapping(value = "/hystrix/breaker/{num}")
    @HystrixCommand(
            fallbackMethod = "circuitBreakerHandler",//注意handler方法要和本方法参数一致
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),
            }
    )
    public String circuitBreaker(@PathVariable("num") Integer num) {
        if (num > 0) {
            return "num:" + num;
        } else {
            throw new IllegalArgumentException("num < 0");
        }
    }

    public String circuitBreakerHandler(Integer num) {
        return "数字不能小于0";
    }
}
