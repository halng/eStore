package com.e.store.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class CorsConfig {

  private static final String ALLOW_HEADER =
      "authorization, Content-Type, Authorization, credential";

  private static final String ALLOW_METHODS = "GET, PUT, PATCH, DELETE, OPTIONS";

  private static final String ALLOW_ORIGINS = "*";

  private static final String MAX_AGE = "3600";

  @Bean
  WebFilter corsWebFilter() {
    return (ServerWebExchange sse, WebFilterChain wfc) -> {
      ServerHttpRequest request = sse.getRequest();
      if (CorsUtils.isCorsRequest(request)) {
        ServerHttpResponse response = sse.getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.set("Access-Control-Allow-Origin", ALLOW_ORIGINS);
        headers.set("Access-Control-Allow-Methods", ALLOW_METHODS);
        headers.set("Access-Control-Max-Age", MAX_AGE);
        headers.set("Access-Control-Allow-Headers", ALLOW_HEADER);
        if (request.getMethod() == HttpMethod.OPTIONS) {
          response.setStatusCode(HttpStatus.OK);
          return Mono.empty();
        }
      }
      return wfc.filter(sse);
    };
  }
}
