package sj.springcould.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class Controller {

    @Value("${config.info}")
    private String info;

    @RequestMapping(value = "/nacos/config")
    public String getInfo() {
        return info;
    }
}
