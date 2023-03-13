package sj.springcloud.seata;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@MapperScan({"sj.springcloud.seata.dao"})//因为exclude = DataSourceAutoConfiguration.clas，所以要配置Dao接口扫描
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //取消数据源的自动创建
public class SeataOrder80 {
    public static void main(String[] args) {
        SpringApplication.run(SeataOrder80.class, args);
    }
}
