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


@Configuration
public class SecurityConfiguration {
	@Autowired
	private AuthenticationFailure authFailure;
	
	@Autowired
	private AuthenticationSuccess authSuccess;

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {      
        http.csrf().disable().httpBasic().disable().authorizeHttpRequests()
        .requestMatchers("/", "/api/v2/login", "/api/v2/who", "/api/v2/logout").permitAll()
        .anyRequest().authenticated().and()
        .formLogin()
			.successHandler(authSuccess)
			.failureHandler(authFailure)
			.loginProcessingUrl("/api/v2/login")
			.usernameParameter("email")
			.passwordParameter("password")
			.permitAll()
	        .and()
	    .logout()
	    	.logoutRequestMatcher(new AntPathRequestMatcher("/api/v2/logout")) // Set custom logout endpoint
	    	.invalidateHttpSession(true)
	    	.deleteCookies("JSESSIONID")
	        .permitAll()
	     ;

        
        http.headers().frameOptions().sameOrigin();
 
        return http.build();
    }
    

    @Bean
    public PasswordEncoder encoder() {
    		PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return encoder;
    }

}
