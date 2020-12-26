package com.msg.todolist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic();
    }
    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        //jdbcAuthentication()
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("SELECT user_name,password FROM user user_name=?")
//                .passwordEncoder(e)

        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}password")
                .roles("USER");
    }
}
