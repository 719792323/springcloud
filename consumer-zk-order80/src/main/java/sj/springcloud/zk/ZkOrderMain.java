package sj.springcloud.zk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ZkOrderMain {
    public static void main(String[] args) {
        SpringApplication.run(ZkOrderMain.class, args);
    }
}
