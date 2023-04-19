package com.sheppo.ApiGateway.config;

import com.sheppo.ApiGateway.common.dto.User;
import com.sheppo.ApiGateway.common.service.UserService;
import com.sheppo.ApiGateway.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

//	private final UserService userService;

//	@Autowired
//	@Lazy
//	private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//	@Autowired
//	@Lazy
//	private AuthenticationProvider authenticationProvider;

//	public SpringSecurityConfiguration(UserService userService) {
//		this.userService = userService;
//	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity){

		serverHttpSecurity.cors().and().csrf().disable()
				.authorizeExchange(exchange -> exchange
						.anyExchange()
						.permitAll());
		return serverHttpSecurity.build();
	}

//	@Bean
//	public UserDetailsService userDetailsService() {
//		return username -> {
//			User userEntity = userService.findUserByUsername(username);
//			if (userEntity == null) {
//				throw new UsernameNotFoundException("Not user with username = " + username);
//			}
//			return (UserDetails) userEntity;
//		};
//	}
//
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//		authenticationProvider.setUserDetailsService(this.userDetailsService());
//		authenticationProvider.setPasswordEncoder(this.passwordEncoder());
//		return authenticationProvider;
//	}
//
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//		return authenticationConfiguration.getAuthenticationManager();
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
}