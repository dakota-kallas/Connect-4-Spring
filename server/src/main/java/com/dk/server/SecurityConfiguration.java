package com.dk.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfiguration {
    // https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
    /*
    @Configuration
    @EnableWebSecurity
    public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .antMatchers("/public/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/home")
                    .permitAll()
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .permitAll();
        }
    }
    */

    @Bean
    public PasswordEncoder encoder() {
    		PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return encoder;
    }

}
