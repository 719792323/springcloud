package sj.springcloud.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

    //可以通过@Bean，配置多个locator
//    @Bean
    public RouteLocator locator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routes = builder.routes();
        routes.route(
                "payment_route_default",//id
                r -> r.path("/consul/**").uri("http://127.0.0.1:8006")
        ).build();
        return routes.build();
    }

//    @Bean
//    public RouteLocator locator2(RouteLocatorBuilder builder) {
//        RouteLocatorBuilder.Builder routes = builder.routes();
//        routes.route(
//                "payment_route_default",//id
//                r -> r.path("xx").uri("xxx")
//        ).build();
//        return routes.build();
//    }
}
