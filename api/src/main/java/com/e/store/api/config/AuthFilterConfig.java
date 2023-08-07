package com.e.store.api.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.client.WebClient;

public class AuthFilterConfig extends AbstractGatewayFilterFactory<AuthFilterConfig.Config> {
    private static final Logger LOG = LoggerFactory.getLogger(AuthFilterConfig.class);
    private final WebClient.Builder webClientBuilder;

    public AuthFilterConfig (WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply (Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            LOG.info("*****************************************************************************");
            LOG.info("Receive new request: " + path);

            if(ExcludeUrlConfig.isSecure(path)) {
                String bearerToken = request.getHeaders().get("Authorization").toString();
                return webClientBuilder.build().get()
                    .uri("http://localhost:9091/v1/auth/validate")
                    .header()
            }
            return chain.filter(exchange);
        };
    }

    public static class Config{
        public Config () {
            // TODO document why this constructor is empty
        }
    }
}
