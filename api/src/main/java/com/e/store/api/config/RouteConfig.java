package com.e.store.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.factory.DedupeResponseHeaderGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

  @Value("${gateway.auth}")
  private String AUTH_URI;

  @Value("${gateway.media}")
  private String MEDIA_URI;

  @Value("${gateway.product}")
  private String PRODUCT_URI;

  @Value("${gateway.inventory}")
  private String INVENTORY_URI;

  @Bean
  public RouteLocator routes(
      RouteLocatorBuilder builder,
      AuthFilterConfig authFilter,
      DedupeResponseHeaderGatewayFilterFactory dedup) {
    return builder
        .routes()
        .route(
            "auth",
            r ->
                r.path("/api/v1/auth/**")
                    .filters(
                        f ->
                            f.rewritePath("/api/v1/auth(?<segment>/?.*)", "/api/v1/auth${segment}")
                                .filter(dedup.apply(dedupeConfig2()))
                                .filter(authFilter.apply(new AuthFilterConfig.Config())))
                    .uri(AUTH_URI))
        .route(
            "product",
            r ->
                r.path("/api/v1/product/**")
                    .filters(
                        f ->
                            f.rewritePath(
                                    "/api/v1/product(?<segment>/?.*)", "/api/v1/product${segment}")
                                .filter(dedup.apply(dedupeConfig2()))
                                .filter(authFilter.apply(new AuthFilterConfig.Config())))
                    .uri(PRODUCT_URI))
        .route(
            "media",
            r ->
                r.path("/api/v1/media/**")
                    .filters(
                        f ->
                            f.rewritePath(
                                    "/api/v1/media(?<segment>/?.*)", "/api/v1/media${segment}")
                                .filter(authFilter.apply(new AuthFilterConfig.Config())))
                    .uri(MEDIA_URI))
        .route(
            "inventory",
            r ->
                r.path("/api/v1/inventory/**")
                    .filters(
                        f ->
                            f.rewritePath(
                                    "/api/v1/inventory(?<segment>/?.*)", "/api/v1/inventory${segment}")
                                .filter(authFilter.apply(new AuthFilterConfig.Config())))
                    .uri(INVENTORY_URI))
        .build();
  }

  @Bean
  public DedupeResponseHeaderGatewayFilterFactory.Config dedupeConfig2() {
    DedupeResponseHeaderGatewayFilterFactory.Config ret =
        new DedupeResponseHeaderGatewayFilterFactory.Config();
    ret.setStrategy(DedupeResponseHeaderGatewayFilterFactory.Strategy.RETAIN_FIRST);
    ret.setName("Access-Control-Allow-Origin");
    return ret;
  }
}
