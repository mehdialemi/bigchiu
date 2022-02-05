package ir.inabama.user.google;

import lombok.Data;

@Data
public class GoogleToken {
    private String accessToken;
    private long expires_at;
    private long expires_in;
    private String idToken;
    private String idpId;
    private String login_hint;
    private String scope;
    private String token_type;
}
