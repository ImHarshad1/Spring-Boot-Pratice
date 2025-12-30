package main.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.Service.EmailService;

@RestController
public class UserController {

	@Autowired
	private EmailService emailService;
	
	@GetMapping("/send")
	public String sendEmail(@RequestParam String toMail) {
		String subject = "Welcome to Spring boot batch";
		String body = "Thank you for registering in spring boot batch , enjoy learning";
		emailService.sendEmail(toMail, subject, body);
		return "Email Sent";	
	}
	
	@GetMapping("/welcome")
	public String getMethodName(@RequestParam String toEmail, @RequestParam String username) throws Exception {
		
		String subject = "Welcome to App";
		
		emailService.sendEmailWithTemplate(toEmail, subject, username);
		
		return "email sent with template";
		
	}
}
