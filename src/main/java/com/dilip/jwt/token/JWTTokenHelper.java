
package com.dilip.jwt.token;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTTokenHelper {
	
 	// Secret key
	private final String SECRET_KEY = "ajjfoffhsifgwe87wyhwr7rhuhy8hfifnhx7xjdhnaih77373hff34rdijcnhkcbndkhcbkcbkjcbcbxhjcbhdkcbdu";
	private final long TOKEN_EXPIRY_DURATION = 5 * 60000; // 5 mins // 1sec = 1000 ms -> 1 min = 60000 ms

	private SecretKey getSecretKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String createToken(String emailId) {


		String token = Jwts.builder()
				.subject(emailId) // unique user id
				.issuedAt(new Date(System.currentTimeMillis())) // setting creation time
				.expiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRY_DURATION)) // setting Expire time
				.signWith(getSecretKey())
				.compact();

		return token;
	}

	public boolean isValidToken(String token, String userId) {
		String userIDFromToken = getUserIdFromToken(token);
		System.out.println("User Id Retrived From Token : " + userIDFromToken);
		return userIDFromToken.equalsIgnoreCase(userId) && isTokenExpired(token);
	}

	// user Id from the token
	public String getUserIdFromToken(String token) {
		return Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}

	// check if the token has expired
	private boolean isTokenExpired(String token) {
		Date expirtyTime = Jwts.parser()
						.verifyWith(getSecretKey())
						.build()
						.parseSignedClaims(token)
						.getPayload()
				.getExpiration();
		System.out.println("Token Epirty Time : " + expirtyTime);
		return expirtyTime.after(new Date());
	}
}
