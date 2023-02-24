package payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ZookeeperPayment8005 {
    public static void main(String[] args) {
        SpringApplication.run(ZookeeperPayment8005.class, args);
    }
}
