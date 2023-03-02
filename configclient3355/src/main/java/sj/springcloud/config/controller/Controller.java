package sj.springcloud.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Value("${version}")
    private String version;

    @RequestMapping(value = "/config/v")
    public String getVersion() {
        return version;
    }
}
