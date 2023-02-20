SpringCloud提供的RestTemplate相当于一个远程调用Rpc程序，通过请求调用指定接口，并将结果封装到JavaBean中。
使用步骤如下：
1. 生成实例，通过@Bean的方式交给Spring管理
```java
@Configuration
public class ApplicationConfig {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}

```
2. 通过提供的Post、Get、Delete、Put方法调用远程接口
```java
@RestController
@Slf4j
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/create")
    public CommonResult create(Payment payment) {
        System.out.println(payment);
        CommonResult forObject = restTemplate.postForObject("http://localhost:8001/payment/create",
                payment, //post数据域
                CommonResult.class //接口返回的json数据对应的javaBean
        );
        return forObject;
    }

    @GetMapping("/consumer/get/{id}")
    public CommonResult getPaymentById(@PathVariable() Long id) {
        CommonResult forObject = restTemplate.getForObject("http://localhost:8001/payment/get/" + id,
                CommonResult.class
        );
        System.out.println(forObject);
        return forObject;
    }
}
```