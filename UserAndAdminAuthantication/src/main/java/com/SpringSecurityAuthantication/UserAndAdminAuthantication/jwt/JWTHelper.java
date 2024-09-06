package com.SpringSecurityAuthantication.UserAndAdminAuthantication.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//Spring annotation to mark the class as a Spring-managed bean.
@Component
public class JWTHelper {
	// Defines a constant for the JWT token validity period, set to 60 minutes (60 seconds * 60).
	public static final long JWT_TOKEN_VALIDITY = 60 * 60;

	// Specifies the secret key used for signing and verifying JWT tokens. This
	// should be kept secure and private.
	private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

	// Extracts the username (subject) from the JWT token by using the Claims::getSubject function.
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// Extracts the expiration date from the JWT token by using the Claims::getExpiration function.
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	// Retrieves a specific claim from the JWT token using a provided claimsResolver function.
	// Calls getAllClaimsFromToken to get all claims
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// Parses the JWT token to extract all claims. Uses the secret key to verify the
	// token’s signature.
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// Checks if the JWT token has expired by comparing the expiration date with the
	// current date
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// Generates a new JWT token for the given UserDetails.
	// Initializes an empty map of claims and passes it along with the username to
	// doGenerateToken.
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	// Creates and returns a JWT token using the specified claims and subject.
	// setClaims(claims): Adds claims to the token.
	// setSubject(subject): Sets the token's subject (username).
	// setIssuedAt: Sets the token’s issue date to the current time.
	// setExpiration: Sets the token’s expiration date by adding the validity period
	// to the current time.
	// signWith(SignatureAlgorithm.HS512, secret): Signs the token using HMAC
	// SHA-512 algorithm and the secret key.
	// compact(): Builds the token string.
	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	// Validates the JWT token by:
	// Extracting the username from the token.
	// Checking if the extracted username matches the provided UserDetails username.
	// Ensuring the token is not expired.
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
