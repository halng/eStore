package com.e.store.auth.constant;

import java.util.Arrays;
import java.util.List;

public class Const {

    public static final String DEFAULT_USER = "SYSTEM";
    public static final Long DEFAULT_TIME_EXPIRY_CONFIRM_ACCOUNT = 86400L;
    public static final List<String> excludedUrl = Arrays.asList("register", "login");
}
