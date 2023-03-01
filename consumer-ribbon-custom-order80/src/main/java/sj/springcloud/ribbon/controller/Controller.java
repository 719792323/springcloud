package sj.springcloud.ribbon.controller;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sj.springcloud.ribbon.irule.LoadBalancer;
import sj.springcloud.ribbon.irule.MyLoadBalancer;

import javax.annotation.Resource;

@RestController
@Slf4j
public class Controller {

    @Resource
    private LoadBalancer myLoadBalancer;

    @Resource
    private RestTemplate restTemplate;


    @RequestMapping(value = "/ribbon/order")
    public String order() {
        ServiceInstance instances = myLoadBalancer.instances();
        log.info("URI:{}",instances.getUri());
        String forObject = restTemplate.getForObject(instances.getUri() + "/consul/payment", String.class);
        return forObject;
    }

}
