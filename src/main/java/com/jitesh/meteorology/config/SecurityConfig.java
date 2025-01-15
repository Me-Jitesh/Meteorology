package com.jitesh.meteorology.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class
SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Authorization
        http
                .csrf().disable()
                .cors().disable()

                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest()
                .authenticated()

                .and()
                .httpBasic()
                .realmName("API")

                // Form Login
                .and()
                .formLogin()
                .loginPage("/login") //GET Method
                .loginProcessingUrl("/submit")    // POST Method From Form
                .failureUrl("/login?invalidCredentials")

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true) // Invalidate session
                .deleteCookies("JSESSIONID")

                // Exception Details
                .and()
                .exceptionHandling()
                .accessDeniedPage("/login?denied");
    }
}
