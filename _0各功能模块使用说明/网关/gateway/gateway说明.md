# 1.gateway的作用与功能
gateway相当于内部网关，而Nginx相当于外部网关，结构如下图所示
![gateway在微服务中架构位置](img/1.png)
## 1.1 gateway的几大功能
### 1.1.1 Route路由
路由的意思就是将一个请求发送到指定的地方，假如外界有一个访问Nginx的请求叫sj.cloud/payment，
gateway接收到这个请求后，要转发到payment这个微服务模块，而在模块payment模块在注册中心注册的服务叫payment-service
所以请求由外界的sj.cloud/payment->nginx->gateway->payment-service/payment
注意：路由可以是ip也可以是dns
### 1.1.2 Predicate断言
断言的作用就是，当收到一个请求时，通过断言来确定将这个请求与哪个路由对应起来（可能找不到对应路由）
假如有一个断言
-Path: /payment/*与路由sj.cloud绑定，那么所有路径地址含/payment/的请求，都会被路由到sj.cloud
gate除了提供了-Path的URL路径地址断言，还提供了如时间断言，Cookie断言等等
如时间断言-After，就表示了，这个请求必须在某个时间之后才会生效
![after断言](img/2.png)
![gate提供的常用断言](img/3.png)
### 1.1.3 Filter过滤器
过滤器的作用就是当某个路由被选中时，可以对Request和Response请求进行操作或者拦截
* 过滤器分为全局过滤器和专用过滤器，全局过滤器对所有进入gateway的请求都其作用，专用过滤器对具体路由起作用。
* 过滤器生命周期有两个pre和post
* 一个请求可以配置多个过滤器

# 2.Gateway的使用
## 2.1必须的依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```
注意：
1. 除了gateway的依赖还需要引入一个服务注册的依赖，如：nacos、consul等
2. gateway服务不能引入spring-boot-starter-web和spring-boot-starter-actuator的依赖，否则无法正常启动
## 2.2 相关配置
```yaml
spring:
  application:
    name: cloud-gateway
  cloud:
    # 注册中心注册，这里用的是consul
    consul:
      host: 192.168.56.128
      port: 8500
      discovery:
        hostname: 127.0.0.1
        service-name: ${spring.application.name}
        heartbeat:
          enabled: true
    # gateway相关配置
    gateway:
      discovery:
        locator:
          # 从注册中心动态创建路由的功能，利用微服务名称进行路由
          enabled: true
      # 路由规则，这里是routes，可以写多个路由规则
      routes:
        # 路由规则的id，每个路由规则要有唯一的id
        - id: payment_route_payment1
          # 路由路径写法
          # 负载均衡路由写法：lb://服务注册中心的服务名称
          uri: lb://cloud-provider-payment
          #直接一个地址或者dns也可以，但是没有负载均衡功能
        # uri: http://127.0.0.1:8006
        # 断言，注意是predicates，所以可以有多个断言规则
          predicates:
            # 路径匹配断言
            - Path=/consul/payment 
```



## 2.3 代码方式配置路由
如下配置了两个路由
```java
@Configuration
public class RouteConfiguration {

    //可以通过@Bean，配置多个locator
    @Bean
    public RouteLocator locator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routes = builder.routes();
        routes.route(
                "payment_route_default",//id
                r -> r.path("/consul/**").uri("http://127.0.0.1:8006")
        ).build();
        return routes.build();
    }

    @Bean
    public RouteLocator locator2(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routes = builder.routes();
        routes.route(
                "payment_route_default",//id
                r -> r.path("xx").uri("xxx")
        ).build();
        return routes.build();
    }
}

```
## 2.4 过滤器配置
filter方法设置过滤器过着，order来设置过滤器优先级
```java
@Component
public class FilterConfiguration implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String uname = request.getQueryParams().getFirst("uname");
        //过滤
        if (uname == null) {
            //设置状态码
            exchange.getResponse().setStatusCode(HttpStatus.BAD_GATEWAY);
            //设置请求完成
            return exchange.getResponse().setComplete();
        }
        // 放行
        return chain.filter(exchange);
    }

    /**
     * 过滤器加载的顺序 越小,优先级别越高
     */
    @Override
    public int getOrder() {
        return 0;
    }

}
```
## 2.5启动类
```java
@SpringBootApplication
@EnableDiscoveryClient
public class GateWay9527 {
    public static void main(String[] args) {
        SpringApplication.run(GateWay9527.class, args);
    }
}
```