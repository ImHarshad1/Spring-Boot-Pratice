package main.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.service.CustomerSerrvice;
import main.service.JwtService;

@Component
public class AppFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;

	@Autowired
	private CustomerSerrvice customerService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = null;
		String userName = null;
		
		// take request header // Read Authorization header (expected: "Bearer <jwt>")
		String authHeader = request.getHeader("Authorization");
		
		// If header present and starts with Bearer, extract token and username
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);					// remove "Bearer "
			userName = jwtService.extractUsername(token);		// read 'sub' claim
		}
		
		// If username found and no authentication yet, try to authenticate
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = customerService.loadUserByUsername(userName);
			
			// Validate token against user details (subject + expiry + signature)
			Boolean isValidate = jwtService.validateToken(token, userDetails); //validating the token
			
			if(isValidate) {
				// Build authentication with authorities
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
				
				// Attach request-specific details (IP, session, etc.)
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// Set authenticated user into the security context
				SecurityContextHolder.getContext().setAuthentication(authToken);	//token is authenticated and can be processed
			}
		}
		// Continue processing the request
		filterChain.doFilter(request, response);
	}
}
