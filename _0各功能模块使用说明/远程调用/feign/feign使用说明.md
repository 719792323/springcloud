# feign使用步骤
feign是一种远程调用工具和RestTemplate比较起来封装的更加深入，通过类似Mybatis一样
使用cglib实现的接口增加，简化了远程调用的发起与数据转换过程。
1. 必要依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```
2. 定义要调用远程接口
其实就是将调用payment模块的controller的方法写成接口类
如Payment的controller的代码如下
```java
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private Integer port;
    private Random random = new Random();
    @GetMapping(value = "/consul/payment")
    public String payment() {
        return "from port:" + port + " you should pay :" + random.nextInt();
    }
    @GetMapping(value = "/consul/timeout")
    public String timeout() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
         return "from port:" + port + "time out 3s";
    }
}
```
那么接口定义成如下
````java
//注意openfeign自带ribbon，所以修改轮询策略的方式就是在ribbon模块介绍的方式
@Component
@FeignClient(value = "cloud-provider-payment") //要调用的微服务名称
public interface PaymentFeignService {

    @GetMapping(value = "/consul/payment")
    String payment();

    @GetMapping(value = "/consul/timeout")
    String timeout();
}
````
注意：
* 在接口上添加@Component和@FeignClient注解
* 注意openfeign自带ribbon，所以修改轮询策略的方式就是在ribbon模块介绍的方式
* @FeignClient中的value是在注册中心的服务名称
3. 在启动类上添加@EnableFeignClients注解
```java
@SpringBootApplication
@EnableFeignClients
public class OpenFeignOrder80 {
    public static void main(String[] args) {
        SpringApplication.run(OpenFeignOrder80.class, args);
    }
}
```
4. Controller注入定义的接口
```java
@RestController
public class Controller {

    //注入接口，实力类由feign通过cglib生成
    @Resource
    PaymentFeignService payment;

    @RequestMapping(value = "/feign/order")
    public String order() {
        return payment.payment();
    }

    @RequestMapping(value = "/feign/timeout")
    public String timeout() {
        return payment.timeout();
    }

}
```

# Feign调用超时问题
默认情况下，Feign的远程调用需要在1s内完成，否则会抛出timeout异常，
如果需要修改默认超时时间，配置方案如下
```yaml
# 因为openFeign使用的是ribbon，所以修改ribbon的超时时间，可以解决连接超时报错的问题
ribbon:
  # 调用rpc超时时间
  ReadTimeout: 5000
  # 连接超时时间
  ConnectTimeout: 5000
```

# Feign开启日志的配置
```yaml
#Feign日志配置
logging:
  level:
    #需要监控的接口的路径以及指定该接口的监控级别
    sj.springcloud.openfegin.service.PaymentFeignService: debug
```