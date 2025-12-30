package main.Service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	// design a method to send a mail
	public void sendEmail(String toMail, String subject, String body) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(toMail);
		message.setText(body);
		message.setSubject(subject);
		
		javaMailSender.send(message);
	}

	@Autowired
	private TemplateEngine templateEngine;
	
	public void sendEmailWithTemplate(String toEmail, String subject, String username) throws Exception {
		
		MimeMessage message = javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		Context context = new Context();
		context.setVariable("username", username);
		
		String body = templateEngine.process("welcome", context);
		
		helper.setTo(toEmail);
		helper.setSubject(subject);
		helper.setText(body, true);
		
		helper.addAttachment("Resume", new File("H:\\Downloads\\Manasi Bhavar Resume (2).pdf"));

		javaMailSender.send(message);
		
		System.out.println("main sent with template");
	}

}
