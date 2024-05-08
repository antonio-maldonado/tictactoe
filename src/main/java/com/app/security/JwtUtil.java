package com.app.security;

import java.util.Date;

import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private SecretKey secretKey = Keys
			.hmacShaKeyFor(";xW&C6;*R*R/6eoQsD6nv\\nfn209d~TBOx".getBytes());

	private static long expiration = 2678400000L;

	public String generateToken(String username,long id) {
		return Jwts.builder()
				.subject(username)
				.id(Long.toString(id))
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(secretKey)
				.compact();
	}

	public String getUsernameFromToken(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload().getSubject();
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
	
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiration.before(new Date());
    }
}
