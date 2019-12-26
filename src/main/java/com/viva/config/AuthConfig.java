package com.viva.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Configuration
@Slf4j
public class AuthConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	AuthenticationSuccessHandler authenticationSuccessHandler;
	//"/","/static/**","/login"
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable();
    	http.authorizeRequests().antMatchers("/about","/contact","/login","/general","/clean-blog.min.css","/register","/styles.css","/saveUser","/home","/hstyle.css","/show/{id}","/search","/home-bg.jpg","/about-bg.jpg","/contact-bg.jpg").permitAll();
        http
            .authorizeRequests()
          	.anyRequest()
          	.authenticated()
          	.and()
          	//.httpBasic();
          	.formLogin()  
            .loginPage("/login")
            .successHandler(authenticationSuccessHandler)
            .permitAll()
            .failureUrl("/login?message=Login Failed")
            .usernameParameter("userName")
            .passwordParameter("password")
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login?message=Logout Successful"); 
        	
            
    }
    
    @Autowired
	UserDetailsService userDetailsService;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
        auth.userDetailsService(userDetailsService);
    }
 
    
}
