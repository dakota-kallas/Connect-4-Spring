package com.dk.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dk.server.security.AuthenticationFailure;
import com.dk.server.security.AuthenticationSuccess;
import com.dk.server.security.EntryPointUnauthorizedHandler;


@Configuration
public class SecurityConfiguration {
	@Autowired
	private AuthenticationFailure authFailure;
	
	@Autowired
	private AuthenticationSuccess authSuccess;
	
	@Autowired
	private EntryPointUnauthorizedHandler authDenied;

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {      
		http
			.authorizeHttpRequests(requests -> requests.requestMatchers("/*", "/api/v2/login", "/api/v2/who", "/api/v2/logout")
				.permitAll()
				.anyRequest().authenticated())
			.formLogin(login -> login
				.successHandler(authSuccess)
				.failureHandler(authFailure)
				.loginProcessingUrl("/api/v2/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.permitAll())
			.logout(logout -> logout
				.permitAll())
			.csrf(csrf -> csrf
				.disable())
			.exceptionHandling(handling -> handling
				.authenticationEntryPoint(authDenied));
        return http.build();
    }
    

    @Bean
    public PasswordEncoder encoder() {
    		PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return encoder;
    }

}
