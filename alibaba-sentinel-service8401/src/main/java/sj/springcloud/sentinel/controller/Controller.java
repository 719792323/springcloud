package sj.springcloud.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sj.springcloud.sentinel.exception.MyException;
import sj.springcloud.sentinel.handler.MyBlockHandler;

@RestController
public class Controller {
    @Value("${server.port}")
    private Integer port;

    @RequestMapping(value = "/sentinel/a")
    public String a() {
        return String.format("port:%s,result:a", port);
    }

    @RequestMapping(value = "/sentinel/b")
    public String b() {
        return String.format("port:%s,result:b", port);
    }


    //使用blockHandler
    @RequestMapping(value = "/sentinel/c")
    @SentinelResource(value = "sr_c", blockHandler = "cHandler")
    public String c(String key, String value) {
        return String.format("port:%s,result:c", port);
    }

    public String cHandler(String key, String value, BlockException e) {
        return "c method error";
    }

    //使用URL的降级规则，测试发现只能转到系统默认降级方法，要使用自定义方法需要用SentinelResource的value值配置规则
    //注意blockHandler要和标注的方法返回值一致
    @RequestMapping(value = "/sentinel/d")
    @SentinelResource(value = "sr_d", blockHandlerClass = MyBlockHandler.class, blockHandler = "handler1")
    public Object d() {
        return String.format("port:%s,result:d", port);
    }

    //blockHandler只会因规则不符合触发，代码运行时异常并不会触发，fallback是管理代码运行时异常
    //注意fallback或者blockHandler要生效，则对应的方法参数和返回值必须和原方法对应，并加上对应的异常参数
    @RequestMapping(value = "/sentinel/e/{id}")
    @SentinelResource(value = "sr_e", blockHandler = "eHandler", fallback = "eErrorHandler",
            exceptionsToIgnore ={MyException.class} //fallback忽略指定异常，忽略的异常会正常抛出
    )
    public Object e(@PathVariable("id") Integer id) {
        if (id == 0) {
            return String.format("port:%s,result:e", port);
        } else if (id == 2) {
            return 1 / 0;
        } else {
            throw  new MyException();
        }
    }

    public Object eHandler(Integer id, BlockException e) {
        return "e method limit";
    }

    public Object eErrorHandler(Integer id, Throwable throwable) {
        return "e method error";
    }


}
