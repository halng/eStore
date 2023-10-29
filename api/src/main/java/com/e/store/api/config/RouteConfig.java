package com.e.store.api.config;

import org.springframework.cloud.gateway.filter.factory.DedupeResponseHeaderGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder, AuthFilterConfig authFilter,
		DedupeResponseHeaderGatewayFilterFactory dedup) {
		return builder.routes().route("auth", r -> r.path("/api/v1/auth/**").filters(
				f -> f.rewritePath("/api/v1/auth(?<segment>/?.*)", "/api/v1/auth${segment}")
					.filter(dedup.apply(dedupeConfig2())).filter(authFilter.apply(new AuthFilterConfig.Config())))
			.uri("http://localhost:9091")).route("product", r -> r.path("/api/v1/product/**").filters(
			f -> f.rewritePath("/api/v1/product(?<segment>/?.*)", "/api/v1/product${segment}")
				.filter(dedup.apply(dedupeConfig2()))
				.filter(authFilter.apply(new AuthFilterConfig.Config()))).uri("http://localhost:9094")).route("media",
			r -> r.path("/api/v1/media/**").filters(
				f -> f.rewritePath("/api/v1/media(?<segment>/?.*)", "/api/v1/media${segment}")
					.filter(authFilter.apply(new AuthFilterConfig.Config()))).uri("http://localhost:9095")).build();
	}

	@Bean
	public DedupeResponseHeaderGatewayFilterFactory.Config dedupeConfig2() {
		DedupeResponseHeaderGatewayFilterFactory.Config ret = new DedupeResponseHeaderGatewayFilterFactory.Config();
		ret.setStrategy(DedupeResponseHeaderGatewayFilterFactory.Strategy.RETAIN_FIRST);
		ret.setName("Access-Control-Allow-Origin");
		return ret;
	}

}
