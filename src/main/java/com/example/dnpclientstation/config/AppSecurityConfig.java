package com.example.dnpclientstation.config;

import com.example.dnpclientstation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private  UserService userService;

    @Autowired
    private  PasswordEncoder passwordEncoder;


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Configuration
    @Order(1)
    public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {


        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/terminal/**")
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic()
                    .and()
                    .headers().httpStrictTransportSecurity().disable()
                    .and()
                    .csrf().disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        }
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService)
                    .passwordEncoder(passwordEncoder);
        }

    }

    @Configuration
    @Order(2)
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/", "/registration", "/activate/*", "/static/**", "/mobile","/mobile/*").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                    .rememberMe().tokenValiditySeconds(86400)
                    .and()
                    .headers().httpStrictTransportSecurity().disable()
                    .and()
                    .logout()
                    .permitAll();


        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService)
                    .passwordEncoder(passwordEncoder);
        }



    }





}

