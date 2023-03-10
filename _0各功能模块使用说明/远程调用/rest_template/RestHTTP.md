RestHTTP的依赖就是spring-web
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```
SpringCloud提供的RestTemplate相当于一个远程调用Rpc程序，通过请求调用指定接口，并将结果封装到JavaBean中。
使用步骤如下：
1. 生成实例，通过@Bean的方式交给Spring管理
```java
@Configuration
public class ApplicationConfig {

    @Bean
    @LoadBalanced //使用eureka需要开启负载均衡，否则会报错
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}

```
注意：开启@LoadBalanced，否则会报错，这个也是使用负载均衡的注解
2. 通过提供的Post、Get、Delete、Put方法调用远程接口
forObject方法，相当于直接获取返回数据，如果调用ForEntity方法
相当于返回一个数据+HTTP信息的的对象
```java
@RestController
@Slf4j
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/create")
    public CommonResult create(Payment payment) {
        System.out.println(payment);
        //调用Post方法
        CommonResult forObject = restTemplate.postForObject("http://localhost:8001/payment/create",
                payment, //post数据域
                CommonResult.class //接口返回的json数据对应的javaBean
        );
        return forObject;
    }

    @GetMapping("/consumer/get/{id}")
    public CommonResult getPaymentById(@PathVariable() Long id) {
        //调用Get方法
        CommonResult forObject = restTemplate.getForObject("http://localhost:8001/payment/get/" + id,
                CommonResult.class
        );
        System.out.println(forObject);
        return forObject;
    }

    @GetMapping("/consumer/create2")
    public CommonResult create2(Payment payment) {
        ResponseEntity<CommonResult> entity = restTemplate.postForEntity(
                PAYMENT_HTTP + "/payment/create",
                payment,
                CommonResult.class
        );
        //entity.getHeaders()获取请求头
        if (entity.getStatusCode().is2xxSuccessful()) {//获取状态码并判断是否是2xx
            return entity.getBody();//返回数据
        }else{
            return null;
        }
    }
}
```