package main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurity {
	
	//Security filter
	@Bean
	public SecurityFilterChain sfc(HttpSecurity http) {
		http.csrf(c -> c.disable())
		.authorizeHttpRequests(req -> req
				.requestMatchers("/login","/help", "/register")
				.permitAll()
				.anyRequest()
				.authenticated());
		return http.build();
	}
	
	//authentication manager
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) {
		return config.getAuthenticationManager();
	}

	//Password Encoder
	@Bean
	public PasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}	
}