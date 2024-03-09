package com.e.store.media.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final RequestFilterConfig requestFilterConfig;

	@Bean
	public SecurityFilterChain httpSecurity(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(
					auth -> auth.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/api-docs/**", "/error")
						.permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/media", "/api/v1/media/**")
						.permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/media", "/api/v1/media/**")
						.hasAuthority("SELLER")
						.anyRequest()
						.authenticated());

		httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable);
		httpSecurity.addFilterBefore(requestFilterConfig, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}

}
