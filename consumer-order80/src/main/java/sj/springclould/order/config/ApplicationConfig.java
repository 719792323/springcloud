package sj.springclould.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    @Bean
    @LoadBalanced //使用eureka需要开启负载均衡，否则会报错
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
