package com.infy.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser("private").password(encoder.encode("secret")).roles("USER")
                .and()
                .withUser("public").password(encoder.encode("public")).roles("PUBLIC");

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2-console").permitAll()
                .antMatchers("/employee/department/**").permitAll()
                .antMatchers("/employee/city/**").hasRole("USER")
                .antMatchers("/**").hasAnyRole("USER", "PUBLIC").anyRequest().authenticated()
                .and()
                .csrf().disable()
                .httpBasic()
                .and()
                .formLogin()
                /*.and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)*/;
    }
}
