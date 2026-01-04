package main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ch.qos.logback.core.encoder.Encoder;
import main.filters.AppFilter;
import main.service.CustomerSerrvice;

@Configuration
@EnableWebSecurity
public class AppSecurity {

	@Autowired
	private UserDetailsService userSerrvice;
	
	@Autowired
	private AppFilter appFilter;
	
	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http) {
		
		http.csrf(c->c.disable())
		.authorizeHttpRequests(req -> req
				.requestMatchers("/help","/register","/login")
				.permitAll()
				.anyRequest()
				.authenticated())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //protocal stateless
			.authenticationProvider(authProvider()) 	//auth provider
			.addFilterBefore(appFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userSerrvice);
		provider.setPasswordEncoder(encoder());
		return provider;
	}

	@Bean
	public AuthenticationManager authMaanger(AuthenticationConfiguration config) {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
