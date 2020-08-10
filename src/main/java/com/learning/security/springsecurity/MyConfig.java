package com.learning.security.springsecurity;

import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//Either it can be configuration or component
@Configuration
public class MyConfig extends WebSecurityConfigurerAdapter {

    //3 things , Authentication and Authorization , password encoder


    //Details of the user in memory
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("siri")
                .password("siri1234")
                .authorities("STUDENT")
                .and()
                .withUser("ashu")
                .password("ashu1234")
                .authorities("FACULTY")
                .and()
                .withUser("admin")
                .password("password")
                .authorities("ADMIN");
    }

    //Q1- Spring security intercepts every request coming on to it? Yes or No


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/student/**").hasAnyAuthority("STUDENT","ADMIN")
                .antMatchers("/faculty/**").hasAnyAuthority("FACULTY","ADMIN")
                .antMatchers("/admin/**").hasAuthority("ADMIN")

                //For general apis no auth
                .antMatchers("/**").permitAll()
                .and()
                .formLogin();
    }

    @Bean
    public PasswordEncoder getMyPassword(){
     //   return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }
}
