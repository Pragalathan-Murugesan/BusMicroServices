package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class GateWayConfiguration {
    @Bean
    public RouteLocator myRoure(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route(predicateSpec -> predicateSpec.
                        path("/api/admin/**")
                        .filters(f -> f.circuitBreaker(c->c.setName("codedTribeCB").setFallbackUri("/fallback/fallback-service1")))
                        .uri("http://localhost:8081"))
                .route(predicateSpec -> predicateSpec.
                        path("/api/user/**")
                        .uri("http://localhost:8082"))
                .build();
    }
//    @Bean
//    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer()
//    {
//        return factory->factory.configureDefault(id ->new Resilience4JConfigBuilder(id)
//                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
//                .timeLimiterConfig(TimeLimiterConfig.custom()
//                        .timeoutDuration(Duration.ofSeconds(2)).build()).build());
//    }


}
