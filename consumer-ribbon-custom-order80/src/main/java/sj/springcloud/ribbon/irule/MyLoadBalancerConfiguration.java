package sj.springcloud.ribbon.irule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyLoadBalancerConfiguration {
    @Bean
    public LoadBalancer loadBalancer() {
        return new MyLoadBalancer("cloud-provider-payment");
    }
}
