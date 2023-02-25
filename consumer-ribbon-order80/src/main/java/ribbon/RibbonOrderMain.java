package ribbon;

import myrule.MyIRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
@RibbonClient(name = "cloud-provider-payment", configuration = MyIRule.class)
public class RibbonOrderMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RibbonOrderMain.class, args);
    }
}
