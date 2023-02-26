package sj.springcloud.ribbon;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
//@RibbonClient(name = "cloud-provider-payment", configuration = MyIRule.class)
public class RibbonOrderCustomMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RibbonOrderCustomMain.class, args);
    }
}
