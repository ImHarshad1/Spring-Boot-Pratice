package main.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import main.dto.LoginDTO;
import main.dto.RegisterDTO;
import main.model.Customer;
import main.repository.CustomerRepository;
import main.service.JwtService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerRepository custRepo;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private PasswordEncoder passEncoder;
	
	@Autowired
	private JwtService jwt;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerMethod(@RequestBody RegisterDTO rd){
		Optional<Customer> opt = custRepo.findByEmail(rd.getEmail());
		if(opt.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body("Already Registered!!!!!!!");
		}
		Customer c = new Customer();
		c.setEmail(rd.getEmail());
		c.setName(rd.getName());
		c.setPassword(passEncoder.encode(rd.getPassword()));
		
		custRepo.save(c);
		return ResponseEntity.status(HttpStatus.CREATED).body("Registered Successfully");
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginMethod(@RequestBody LoginDTO ld){
		try {			// Generate JWT and Send it back to the client
			UsernamePasswordAuthenticationToken holds = new UsernamePasswordAuthenticationToken(ld.getEmail(),ld.getPassword());
			
			Authentication auth = authManager.authenticate(holds);
					   //The AuthenticationManager takes token and passes it to the configured AuthenticationProvider
			
			if(auth.isAuthenticated()) {//After authentication, this isAuthenticated(): Confirms login success.
					// Generate JWT and Send it back to the client
				
				return ResponseEntity.status(HttpStatus.OK).body("Logged In Successful");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Credentials");
	}
}


