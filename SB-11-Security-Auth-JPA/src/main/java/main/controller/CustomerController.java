package main.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import main.dto.LoginDTO;
import main.dto.RegisterDTO;
import main.model.Customer;
import main.repository.CustomerRepository;

@RestController
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerdMethod(@RequestBody RegisterDTO registerDTO){
		Optional<Customer> opt = customerRepo.findByEmail(registerDTO.getEmail());
		if(opt.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body("Already Registered");
		}
		Customer c = new Customer();
		c.setName(registerDTO.getName());
		c.setEmail(registerDTO.getEmail());
		c.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

		customerRepo.save(c);
		return ResponseEntity.status(HttpStatus.CREATED).body("Registered Successfully");
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> LoginMethod(@RequestBody LoginDTO loginDTO){
		try {
			UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword());
		
			Authentication auth = authenticationManager.authenticate(t);
			if(auth.isAuthenticated()) {
				return ResponseEntity.status(HttpStatus.OK).body("Logged In Successful");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Credentials");
	}
 
	@GetMapping("/greet")
	public String getMsg() {
		return "Hey Good Morning!!!!!!!!!!!!!";
	}
}
