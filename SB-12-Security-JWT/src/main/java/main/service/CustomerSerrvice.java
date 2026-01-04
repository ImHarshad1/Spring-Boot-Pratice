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
public class CustomerSerrvice implements UserDetailsService{
	@Autowired
	private CustomerRepository custReposo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Customer c = custReposo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email));
		
 		return User.builder()
 				.username(c.getEmail())
 				.password(c.getPassword())
 				.build();
	}
}
