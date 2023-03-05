package sj.springcloud.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import rule.RuleConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@RibbonClient(name = "alibaba-nacos-payment", configuration = RuleConfiguration.class)
public class Nacos80 {
    public static void main(String[] args) {
        SpringApplication.run(Nacos80.class, args);
    }
}
