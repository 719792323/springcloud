# 公共使用步骤
1. hystrix依赖
```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    </dependency>
```
2. 启动类注解@EnableCircuitBreaker/@EnableHystrix
```java
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
//@EnableCircuitBreaker
//@EnableHystrix和@EnableCircuitBreaker是等价的写一个就行
@EnableFeignClients
public class HystrixOrder80 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixOrder80.class, args);
    }
}
```
# 服务降级
服务降级就是当调用的某个方法发送异常之后，会切换到处理异常方法进行处理并返回结果
在hystrix可能会导致服务降级的原因有：
* 程序运行异常（抛出的异常）
* 方法运行超时（需要指定超时时长）
* 服务熔断生效
* 线程池/信号量（线程池无可服务线程/信号量不够p）
## 使用步骤
1. 配置公共步骤
2. @HystrixCommand与@HystrixProperty
在需要调用的方法上打上@HystrixCommand，并指定fallbackMethod方法，该方法需要与标注
@HystrixCommand在同一个类内。@HystrixProperty指定调用超时时间，调用超过该时间会进入fallbackMethod。
完成后，如果调用标注了@HystrixCommand的方法发生上面所列的任何一个问题时，都会转向调用fallbackMethod指定的方法。
注意：原理其实是对该方法使用了cglib动态代理，且代理的方法所涉及的类了属性还是原类的属性
假设原方法
```text
class Ori{
    private filed
    @HystrixCommand
    public a（）{
        使用field
    }
    public aHandler(){

    }
}
那么hystrix生成的代理
class OriCglib{
   private filed（就是Ori中的field）
   public a$cglib(){
        try{
            a()//调用原来的方法
        }catch{
            aHandler()//发生异常后调用ahandler
        }
   }
}
```
如下是一个使用方法实例
```text
   @HystrixCommand(fallbackMethod = "timeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")})
    @RequestMapping(value = "/hystrix/timeout")
    public String timeout() {
        return paymentService.timeout();
    }
    
    public String timeoutHandler() {
        return "paymentService.timeout():timeout";
    }
```
3. 全局配置@DefaultProperties
指定默认异常处理方法，如果标注了@HystrixCommand的方法，但是没有指定fallbackMethod，
则会使用defaultFallback指定的方法，如果指定了，则还是用指定的方法处理
没有标注的@HystrixCommand不会受任何影响
```text
@RestController
@Slf4j
@DefaultProperties(defaultFallback = "error")
public class Controller {
}
```

## feign与hystrix的服务降级整合
作用是对feign接口方法的降级一对一方案
如下是feign的远程调用接口,通过使用@FeignClient加上fallback指定错误降级类可实现，
调用接口方法失败或者其他触发服务降级情况时，自动调用PaymentServiceFallbackImpl的方法
自动调用PaymentServiceFallbackImpl的方法是PaymentService的实现类，该类中实现的是服务降级的一对一方法
```text
@Component
@FeignClient(value = "cloud-provider-hystrix-payment",
        fallback = PaymentServiceFallbackImpl.class)//指定调用接口时对应的服务降级类，该类必须实现本接口（PaymentService）
public interface PaymentService {
    @RequestMapping(value = "/hystrix/ok")
    String ok();

    @RequestMapping(value = "/hystrix/timeout")
    String timeout();
}
```
```text
@Component
public class PaymentServiceFallbackImpl implements PaymentService{
    @Override
    public String ok() {
        return "PaymentServiceFallbackImpl:ok";
    }

    @Override
    public String timeout() {
        return "PaymentServiceFallbackImpl:timeout";
    }
}
```
注意需要开启如下配置才能实际生效
```yaml
#打开后，既启用hystrix对feign的fallback接口代理
feign:
  hystrix:
    enabled: true
```
# 服务熔断
当一个方法多出出现会导致服务降级的情况时，让其暂时不可用（既就算该方法能正常使用了，也让他调用服务降级方法）进入熔断状态，
过一段时间后如果能正常访问，则关闭熔断状态。
下面几个注解是配置熔断相关的，下面的配置表示，在一个长度为10s的熔断窗口内，如果10此方法调用有6次发生了降级，
则进入熔断状态
@HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //开启熔断
@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //熔断窗口内统计单位
@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), //熔断窗口时间长度
@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//发送服务降级比例
```text
  //服务熔断
    @RequestMapping(value = "/hystrix/breaker/{num}")
    @HystrixCommand(
            fallbackMethod = "circuitBreakerHandler",//注意handler方法要和本方法参数一致
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),
            }
    )
    public String circuitBreaker(@PathVariable("num") Integer num) {
        if (num > 0) {
            return "num:" + num;
        } else {
            throw new IllegalArgumentException("num < 0");
        }
    }
    //降级方法
    public String circuitBreakerHandler(Integer num) {
        return "数字不能小于0";
    }
```

# 服务限流