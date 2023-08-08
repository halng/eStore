package com.e.store.api.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator routes(
        RouteLocatorBuilder builder,
        AuthFilterConfig authFilter) {
        return builder.routes()
                      .route("auth", r -> r.path("/api/v1/auth/**")
                                                         .filters(f ->
                                                                      f.rewritePath("/api/v1/auth(?<segment>/?.*)", "${segment}")
                                                                       .filter(authFilter.apply(
                                                                           new AuthFilterConfig.Config())))
                                                         .uri("http://localhost:9091"))
                      .route("product", r -> r.path("/api/v1/product/**")
                                                         .filters(f ->
                                                                      f.rewritePath("/api/v1/producte(?<segment>/?.*)", "${segment}")
                                                                       .filter(authFilter.apply(
                                                                           new AuthFilterConfig.Config())))
                                                         .uri("http://localhost:9093"))
                      .build();
    }
}
