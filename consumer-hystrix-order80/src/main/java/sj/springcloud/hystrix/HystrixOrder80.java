package sj.springcloud.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
//@EnableHystrix和@EnableCircuitBreaker是等价的写一个就行
@EnableFeignClients
public class HystrixOrder80 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixOrder80.class, args);
    }
}
