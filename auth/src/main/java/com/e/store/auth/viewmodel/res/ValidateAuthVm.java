package com.e.store.auth.viewmodel.res;


import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public record ValidateAuthVm(String username, String authority) {
}
