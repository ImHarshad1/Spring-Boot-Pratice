package main.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	// Secret key used for signing and verifying JWT tokens 
	// NOTE: Must be Base64 encoded string for Decoders.BASE64 to work
	public static final String SECRET ="357638792F423F4428472B4B6250655368566D597133743677397A2443264629";
	
	// Extracts username (stored as 'subject' in JWT payload)
	public String extractUsername (String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// Extracts expiration date from JWT
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	// Generic method to extract any claim using a resolver function
	public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
		final Claims claims = extractAllClaims(token);	// parse token to get all claims
			return claimsResolver.apply(claims);    	// apply resolver (subject, expiration, etc.)
	}
	
	// Parses the token and returns all claims (payload data)
	private Claims extractAllClaims(String token) {
		return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())		  // use secret key to verify signature
                .build()
                .parseClaimsJws(token)				  // parse and validate token
                .getBody();							  // return claims (payload)
	}
	
	// Checks if token is expired by comparing expiration with current time
	private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

	// Validates token: username must match AND token must not be expired
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

 // Generates a new JWT token for given username
    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>(); // you can add extra info here if needed
        return createToken(claims, username);
    }
    
 // Builds the JWT with claims, subject, issue time, expiry, and signature
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)												// custom data (empty now)
                .setSubject(username)											// payload: username
                .setIssuedAt(new Date(System.currentTimeMillis()))				// issued time
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60)) //one hour from token generated
                .signWith(getSignKey(), SignatureAlgorithm.HS256)				// sign with secret key  
                .compact();														// build final token string
    }

 // Converts secret string into a signing key (HMAC-SHA256)
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET); 		// decode Base64 secret return Keys.hmacShaKeyFor(keyBytes); 
        return Keys.hmacShaKeyFor(keyBytes);					// generate key for signing
    }
}
