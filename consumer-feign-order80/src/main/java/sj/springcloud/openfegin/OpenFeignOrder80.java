package sj.springcloud.openfegin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OpenFeignOrder80 {

    public static void main(String[] args) {
        SpringApplication.run(OpenFeignOrder80.class, args);
    }
}
