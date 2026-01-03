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
	private CustomerRepository custRepo;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private PasswordEncoder passEncode;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerMethod(@RequestBody RegisterDTO rd){
		Optional<Customer> opt = custRepo.findByEmail(rd.getEmail());
		if(opt.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body("Already Registered !!!!!!");
		}
		
		Customer c = new Customer();
		c.setName(rd.getName());
		c.setEmail(rd.getEmail());
		c.setPassword(passEncode.encode(rd.getPassword()));
		
		custRepo.save(c);
		return ResponseEntity.status(HttpStatus.CREATED).body("Registered Successfully");
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginMethod(@RequestBody LoginDTO ld){
		try {		//this holds the user’s credentials and it’s unauthenticated — just raw credentials.
			UsernamePasswordAuthenticationToken holds = new UsernamePasswordAuthenticationToken(ld.getEmail(), ld.getPassword());
			
			Authentication auth = authManager.authenticate(holds);		
			//The AuthenticationManager takes token and passes it to the configured AuthenticationProvider

			if(auth.isAuthenticated()) {	//After authentication, this isAuthenticated(): Confirms login success.
				return ResponseEntity.status(HttpStatus.OK).body("Logged In Successful");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Credentials");
	}

	@GetMapping("/greet")
	public String greet() {
		return "Hiiii!!!!";
	}
}