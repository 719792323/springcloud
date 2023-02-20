package sj.springcloud.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient//启用服务发现功能(要使用DiscoveryClient,需要该注解)
public class Payment8001 {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(Payment8001.class, args);

        //注意：是这个包下的DiscoveryClient
        //import org.springframework.cloud.client.discovery.DiscoveryClient;
        DiscoveryClient discoveryClient = run.getBean(DiscoveryClient.class);
        //如果获取到的是空，可能是通信还没建立好，需要等待一会 Thread.sleep(5000);
        Thread.sleep(3000);
        //获取application这一列
        List<String> services = discoveryClient.getServices();
        System.out.println(services);
        for (String service : services) {
            //status这一列
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                System.out.println(instance.getServiceId());
                System.out.println(instance.getInstanceId());
                System.out.println(instance.getHost());
                System.out.println(instance.getPort());
                System.out.println(instance.getUri());
            }
        }
    }
}
