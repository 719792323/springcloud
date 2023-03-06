package sj.springcloud.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class Controller {

    @Resource
    RestTemplate restTemplate;


    @Value("service-url.nacos-user-service")
    private String SERVER_URL;

    @RequestMapping(value = "/nacos/info")
    public String info() {
        return restTemplate.getForObject("http://alibaba-nacos-payment/nacos/info", String.class);
    }
}
