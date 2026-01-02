package main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import main.model.Customer;
import main.repository.CustomerRepository;

public class CustomerService implements UserDetailsService{

	//used to Load users from your database.
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Customer c = customerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
		
		return User.builder()
				.username(c.getEmail())
				.password(c.getPassword())
				.build();
	}
}
