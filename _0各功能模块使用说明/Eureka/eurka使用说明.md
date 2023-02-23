# eureka作用
eureka的作用是将一个SpringBoot项目用作于服务注册中心
eureka项目中有三个角色，client（用户端）、service（服务端提供调用）、server（注册中心）
具体运作的流程是，service（如支付模块）向server（eureka节点）注册自己，client（消费模块）向server
拉取可用的service信息，然后client向service模块消费

# server服务端使用步骤
1. 创建一个eureka子模块
2. 子模块必须的依赖
```xml
   <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
   <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
```
3. 子模块项目配置
* 指定server.port端口号，指定该eureka项目启动端口
* eureka单节点配置(以eureka7001项目为例)
```yaml
eureka项目中有三个角色，client（用户端）、service（服务端提供调用）、server（注册中心）
eureka7001充当server（注册中心）作用
eureka:
  instance:
    hostname: localhost #当节点写localhost就行，也可以指定具体hostname
  client:
    register-with-eureka: false #是否向eureka注册：注册中心server端不注册自己，注册的是具体功能模块（service），比如支付模块
    fetch-registry: false #是否要拉取已注册的项目：server端不需要拉取注册服务，一般是用户端拉取注册的功能模块
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```
* eureka集群配置
```yaml
eureka:
  instance:
    hostname: eureka7001.com #本eureka对应的host
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      #其他eurekaServer的网络地址（不包括自己），其他eurekaServer也是不写自己写其他成员的服务地址
      defaultZone: http://eureka7002.com:7002/eureka/
  #关闭自我保护模式
  server:
    enable-self-preservation: false
```
4. @EnableEurekaServer注解
在SpringBootApplication上加上该注解并启动，该项目成位eureka服务端
```java
@SpringBootApplication
@EnableEurekaServer //表示该项目作为eureka服务注册中心
public class EurekaMain {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(EurekaMain.class, args);
    }
}
```


# service端使用步骤
1. 必须的依赖
```xml
        <!--引入eureka-client-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```
2. 相关配置
```yaml

spring:
  #模块的名称会在eureka的webUI显示，同一类功能模块，取相同的模块名称，比如payment8001和payment8002都用这个
  application:
    name: cloud-payment-service
    
eureka:
  client:
    register-with-eureka: true   #service段提供服务，所以需要注册进入eureka
    fetch-registry: true #如果server是eureka集群，此处必须设置为true才能搭配ribbon进行负载均衡
    service-url:
      #eureka_server集群成员的接入地址
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka/
  instance:
    instance-id: payment8001 #本service模块的名称
    prefer-ip-address: true #该配置决定在eureka的webui的status这一列的instance-id是否显示对应主机service的ip地址（true显示）
```
3. @EnableEurekaClient注解
Service端需要在SpringBootApplication上加上该注解
```java
@SpringBootApplication
@EnableEurekaClient
public class Payment8002 {
    
}
```
4. @EnableDiscoveryClient
如果需要使用服务发现功能（使用eureka不需要搭配），需要在SpringBootApplication上加上该注解
```java
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient//启用服务发现功能(要使用DiscoveryClient,需要该注解)
public class Payment8001 {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(Payment8001.class, args);

        //注意：是这个包下的DiscoveryClient
        //import org.springframework.cloud.client.discovery.DiscoveryClient;
        DiscoveryClient discoveryClient = run.getBean(DiscoveryClient.class);
        //如果获取到的是空，可能是通信还没建立好，需要等待一会 Thread.sleep(5000);
        Thread.sleep(3000);
        //获取eurekaWebUI中application这一列
        List<String> services = discoveryClient.getServices();
        System.out.println(services);
        for (String service : services) {
            //status这一列
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                System.out.println(instance.getServiceId());
                System.out.println(instance.getInstanceId());
                System.out.println(instance.getHost());
                System.out.println(instance.getPort());
                System.out.println(instance.getUri());
            }
        }
    }
}
```


# Client端使用步骤
1. 必须的依赖
```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   </dependency>
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
```
2. 相关配置
```yaml
    spring:
      application:
        name: cloud-order-service
    eureka:
    client:
      register-with-eureka: true
      fetch-registry: true
      service-url:
        defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka/
    instance:
      instance-id: order80
      prefer-ip-address: true
```
3. @EnableEurekaClient
```java
    @SpringBootApplication
    @EnableEurekaClient
    public class Order80 {
        public static void main(String[] args) {
            SpringApplication.run(Order80.class, args);
        }
    }
```
4. 搭配RestHTTP进行负载均衡访问
参考RestHTTP.md