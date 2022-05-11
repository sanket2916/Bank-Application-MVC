package com.kaneki.springboot.bankapplication.security;

import com.kaneki.springboot.bankapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("appDataSource")
    private DataSource securityDataSource;

    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/customers").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/customers/list").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/customers/customer-form").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.POST,"/api/customers").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/api/customers/{customerId}").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/customers").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/api/customers/transactions/**").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
                .antMatchers(HttpMethod.POST,"/api/customers/transactions/**").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/customers/{customerId}").hasRole("ADMIN")
                .antMatchers("/customers/showFormToAddCustomer").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/customers/showFormToUpdateCustomer").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.POST,"/customers/saveCustomer").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/customers/delete").hasRole("ADMIN")
                .antMatchers("/api/users").hasRole("ADMIN")
                .antMatchers("/users/**").hasRole("ADMIN")
                .and()
                .formLogin()
                    .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}