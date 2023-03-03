package sj.springcloud.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StreamProducer8801 {
    public static void main(String[] args) {
        SpringApplication.run(StreamProducer8801.class, args);
    }
}
