package sj.springcloud.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@Slf4j
public class Controller {

    @Value("${server.port}")
    private Integer port;

    private Random random = new Random();

    @RequestMapping(value = "/hystrix/ok")
    public String ok() {
        log.info("{}", random.hashCode());
        return Thread.currentThread().getName() + "port:" + port + ",ok";
    }
    //发生显示异常或者指定的特殊异常都会触发fallbackMethod
    @RequestMapping(value = "/hystrix/timeout")
    @HystrixCommand(fallbackMethod = "timeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")})
    public String timeout() {
        log.info("{}", random.hashCode());
//        int i = 1 / 0; 显示异常
        try {
            Thread.sleep(3000); //特殊异常（超时异常，这种并不会异常是使用者自己定义的，并不会实际产生exception）
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Thread.currentThread().getName() + "port:" + port + ",timeout";
    }

    public String timeoutHandler() {
        return Thread.currentThread().getName() + "port:" + port + ",sorry timeout";
    }


}
