package com.e.store.auth.config.jwt;

import com.e.store.auth.services.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtilities           jwtUtilities;
    private final UserDetailsServiceImpl userDetailsService;

    private static final List<String> LIST_EXCLUDE_URL = Arrays.asList("register", "login", "active-token");

    private boolean isExclude(String path) {
        for (String s: LIST_EXCLUDE_URL){
            if (path.contains(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
        if (!isExclude(request.getRequestURI())) {
            String accessToken = jwtUtilities.getAccessToken(request);

            if ( accessToken != null && jwtUtilities.validateAccessToken(accessToken) ) {
                String      username    = jwtUtilities.getUsernameFromAccessToken(accessToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if ( userDetails != null && jwtUtilities.validateAccessToken(accessToken, userDetails) ) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(), null, userDetails.getAuthorities());
                    log.info("User %s authenticated.".formatted(username));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
