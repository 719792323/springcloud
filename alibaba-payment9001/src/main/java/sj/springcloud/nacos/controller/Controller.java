package sj.springcloud.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Value("${server.port}")
    private Integer port;

    @RequestMapping(value = "/nacos/info")
    public String info() {
        return String.format("port:%s", port);
    }

}
