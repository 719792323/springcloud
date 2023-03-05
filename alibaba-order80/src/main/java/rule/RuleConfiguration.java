package rule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuleConfiguration {

    @Bean
    public IRule myRule() {
        // 定义为随机的负载均衡策略
        return new RandomRule();
    }
}
