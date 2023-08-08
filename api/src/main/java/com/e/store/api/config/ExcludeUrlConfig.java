package com.e.store.api.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.server.ServerHttpRequest;

public class ExcludeUrlConfig {

    private static final List<String> excludedUrl = List.of("auth");

    public static boolean isSecure(String requestPath) {
        for(String ex:excludedUrl) {
            if(requestPath.contains(ex)) {
                return false;
            }
        }
        return true;
    }
}
