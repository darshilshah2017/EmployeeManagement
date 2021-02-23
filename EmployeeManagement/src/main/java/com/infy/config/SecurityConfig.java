package com.infy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        String encoded = encoder.encode("secret");
        System.out.println("encoded::: "+encoded);

        auth.jdbcAuthentication().dataSource(dataSource)
                /*.usersByUsernameQuery("select username,password,enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from user_roles where username=?")*/;

    }

//    We should use the  in-memory authentication method for testing purpose only.
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

//        User authentication credentials.
        auth.inMemoryAuthentication()
                .withUser("private").password(encoder.encode("secret")).roles("USER")
                .and()
                .withUser("public").password(encoder.encode("public")).roles("PUBLIC");

    }*/


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        Authorization of URL's
        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
//                .antMatchers("/employee/department/**").permitAll()
                .antMatchers("/employee/city/**").hasRole("USER")
                .antMatchers("/**").hasAnyRole("USER", "PUBLIC").anyRequest().authenticated();

//        Used for exposing all the operations over SSL i.e. HTTPS
        http.requiresChannel().anyRequest().requiresSecure();

//        Only 1 session will exists for any user. If we try to open multiple sessions, then we will receive the below error for one of the session
//        Error message: This session has been expired (possibly due to multiple concurrent logins being attempted as the same user).
        http.sessionManagement().maximumSessions(1);

        http.formLogin().loginPage("/login").defaultSuccessUrl("",true);
        
        http.csrf().disable()
                .httpBasic()
                .and()
                .formLogin()
                /*.and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)*/;

//        Below configuration is used for enabling h2-console when using spring security
        http.headers().frameOptions().disable();
    }
}
