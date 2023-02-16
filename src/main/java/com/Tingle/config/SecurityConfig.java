package com.Tingle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.formLogin()
		.loginPage("/members/login")
		.defaultSuccessUrl("/")
		.usernameParameter("email")
		.failureUrl("/members/login/error")
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
		.logoutSuccessUrl("/");
		
		http.authorizeRequests()
		.mvcMatchers("/css/**","/js/**","/fonts/**","/images/**","/img/**").permitAll()
		.mvcMatchers("/","/members/**","/item/**").permitAll();
		//.anyRequest().authenticated();
		
		
		http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

		// http.csrf().disable();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS); //모든 접속(?)리퀘스트에 대하여 항상 session을 만들어줍니다.
		
		return http.build();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
}
