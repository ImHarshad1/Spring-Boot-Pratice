package main.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import main.model.Customer;
import main.repository.CustomerRepository;
	@Service
	public class CustomerService implements UserDetailsService{
	//In Spring Security, authentication is based on a UserDetails object.
		
		//used to Load users from your database.
		// CustomerService implements UserDetailsService for Spring Security.
		// loadUserByUsername() is called during login.
		// It fetches Customer from DB using email.
		// If found, converts Customer into a Spring Security UserDetails object.
		// This UserDetails contains username, password (encoded).
		// Spring Security then uses this info to authenticate the user.

		
		@Autowired
		private CustomerRepository custRepository;
		
		@Override
		public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			
			Customer c = custRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
			
			return User.builder()
					.username(c.getEmail())
					.password(c.getPassword())
					.build();
		}	
}