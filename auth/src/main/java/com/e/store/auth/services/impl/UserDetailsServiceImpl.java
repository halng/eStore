package com.e.store.auth.services.impl;

import com.e.store.auth.entity.Account;
import com.e.store.auth.repositories.IAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IAuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account =
            authRepository
                .findByUsername(username)
                .orElseThrow(
                    () -> new UsernameNotFoundException("Username " + username + " not found"));
        return new User(account.getUsername(), account.getPassword(), account.getAuthorities());
    }
}
