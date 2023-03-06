package sj.springcloud.stream.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sj.springcloud.stream.service.Producer;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
public class Controller {
    @Resource
    private Producer producer;

    @RequestMapping(value = "/producer/send")
    public String send() {
        String s = UUID.randomUUID().toString();
        boolean send = producer.send(s);
        return String.format("send:%s,result:%s", s, send);
    }
}
