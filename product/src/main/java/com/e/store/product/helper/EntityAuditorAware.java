package com.e.store.product.helper;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class EntityAuditorAware implements AuditorAware<String> {
    @Override
    public Optional getCurrentAuditor() {
        /**
         * TODO: Need to implement code to call real user name.
         * Maybe we need to call to auth service to get real name or find a new mechanism
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            return Optional.of("USER");
        }
        return Optional.of("SYSTEM");
    }
}
