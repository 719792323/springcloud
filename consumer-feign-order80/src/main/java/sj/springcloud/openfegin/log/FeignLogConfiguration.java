package sj.springcloud.openfegin.log;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignLogConfiguration {
    //注意Logger是feign.Logger这个包
    @Bean
    public Logger.Level feignLoggerLever(){
        return Logger.Level.FULL;
    }
}
