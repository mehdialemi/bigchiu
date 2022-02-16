package ir.inabama.user.jwt;

import io.jsonwebtoken.*;
import ir.inabama.user.common.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtService {

	@Value("${jwt.token:abcdefghi}")
	private String jwtSecret;

	@Value("${jwt.expiration:100000}")
	private int jwtExpirationInMs;

	@Autowired
	private AuthenticationManager authManager;

	public JwtToken createToken(Authentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String accessToken = generateToken(authentication);
		JwtToken jwt = new JwtToken();
		jwt.setAccessToken(accessToken);
		return jwt;
	}

	public JwtToken createToken(String username, String password) {
		return createToken(authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)));
	}

	public String generateToken(Authentication authentication) {

		UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		log.debug("Creating JWT token for username: {}", principal.getUsername());
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", principal.getUsername());
		claims.put("password", principal.getPassword());
		claims.put("authorities", principal.getAuthorities().stream()
				.map(a -> a.getAuthority())
				.collect(Collectors.toList()));

		String token = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
		log.info("Jwt token is created for username: " + principal.getUsername() + ", token: " + token);
		return token;
	}

	public UserPrincipal getUserDetails(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();
		String username = claims.get("username", String.class);
		String password = claims.get("password", String.class);
		List<GrantedAuthority> authorities = ((List<String>) claims.get("authorities")).stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		UserPrincipal principal = new UserPrincipal();
		principal.setUsername(username);
		principal.setPassword(password);
		principal.setAuthorities(authorities);
		return principal;
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			log.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}
}
