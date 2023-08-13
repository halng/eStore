package com.e.store.api.config;

import java.util.List;


public class ExcludeUrlConfig {

    private static final List<String> excludedUrl = List.of("register", "login", "active-account");

    public static boolean isSecure(String requestPath) {
        for(String ex:excludedUrl) {
            if(requestPath.contains(ex)) {
                return false;
            }
        }
        return true;
    }
}
