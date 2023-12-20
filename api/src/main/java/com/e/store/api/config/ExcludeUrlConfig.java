package com.e.store.api.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpMethod;

public class ExcludeUrlConfig {

    private static final Map<String, HttpMethod> excludedUrl;

    static {
        excludedUrl = new HashMap<>();
        excludedUrl.put("register", HttpMethod.POST);
        excludedUrl.put("login", HttpMethod.POST);
        excludedUrl.put("image", HttpMethod.GET);
        excludedUrl.put("active-account", HttpMethod.POST);
    }

    public static boolean isSecure(String requestPath, HttpMethod method) {
        for (Map.Entry ex : excludedUrl.entrySet()) {
            if (requestPath.contains(ex.getKey().toString()) && method.equals(ex.getValue())) {
                return false;
            }
        }
        return true;
    }
}
