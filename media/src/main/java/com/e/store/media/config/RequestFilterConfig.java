package com.e.store.media.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class RequestFilterConfig extends OncePerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(RequestFilterConfig.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String username = request.getHeader("username");
		String authority = request.getHeader("authority");

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
		if (username != null && authority != null) {
			List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
			grantedAuthorityList.add(new SimpleGrantedAuthority(authority));
			usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null,
				grantedAuthorityList);
		}

		LOG.info(String.format("Receive a request %s from %s", request.getRequestURI(), username));

		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		filterChain.doFilter(request, response);
	}
}
