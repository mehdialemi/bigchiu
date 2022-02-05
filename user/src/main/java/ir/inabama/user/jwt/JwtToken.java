package ir.inabama.user.jwt;

import lombok.Data;

@Data
public class JwtToken {
	public static final String TOKEN_TYPE = "Bearer";
	private String accessToken;
}
