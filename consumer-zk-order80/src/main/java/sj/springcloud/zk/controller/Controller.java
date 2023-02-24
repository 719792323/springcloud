package sj.springcloud.zk.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class Controller {

    @Resource
    private RestTemplate restTemplate;

    public static final String INVOKE_URL = "http://cloud-provider-payment";

    @RequestMapping(value = "/zk/order")
    public String order() {
        String forObject = restTemplate.getForObject(INVOKE_URL + "/zk/payment", String.class);
        return forObject;
    }

}
