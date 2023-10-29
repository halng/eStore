package com.e.store.product.services.impl;

import org.springframework.security.core.context.SecurityContextHolder;

public class CommonService {
  public static String getUser() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
