package sj.springcloud.ribbon.irule;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class MyLoadBalancer implements LoadBalancer {
    @Resource
    private DiscoveryClient discoveryClient;
    private AtomicInteger round = new AtomicInteger(0);
    private String serviceId;

    public MyLoadBalancer(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public ServiceInstance instances() {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        int size = instances.size();
        int i = round.get();
        int index = i % size;
        while (!round.compareAndSet(i, index+1)) ;
        return instances.get(index);
    }
}
