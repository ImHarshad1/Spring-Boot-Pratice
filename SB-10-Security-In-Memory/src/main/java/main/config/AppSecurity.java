package main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurity {

	// In-memory
	@Bean
	public UserDetailsService getuserDetailsService() {
		
		UserDetails Public = User.builder()
				.username("Dhoni")
				.password(passwordEncoder().encode("dhoni07"))
				.roles("PUBLIC")
				.build();
		
		UserDetails user = User.builder()
				.username("CSK")
				.password(passwordEncoder().encode("CSK07"))
				.roles("USER", "ADMIN")
				.build();
		
		UserDetails admin = User.builder()
				.username("RCB")
				.password(passwordEncoder().encode("RCB18"))
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(Public, user, admin);
	}
	
	// FilterChain
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		System.out.println(">>> SecurityFilterChain loaded!");
		http.csrf(c -> c.disable())
				.authorizeHttpRequests(req -> req
						.requestMatchers("/public/**")
						.permitAll()
						.requestMatchers("/user/**")
						.hasAnyRole("USER", "ADMIN")
						.requestMatchers("/admin/**")
						.hasRole("ADMIN")
						.anyRequest()
						.authenticated()
					)
				.formLogin(Customizer.withDefaults())
				.logout(Customizer.withDefaults());
		
		return http.build();
	}
	
	// passwordEncoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}	