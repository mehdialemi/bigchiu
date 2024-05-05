package ir.inabama.auth.google;

public class GoogleLoginRequest {

    private ProfileObj profileObj;
    private TokenObj tokenObj;

    public ProfileObj getProfileObj() {
        return profileObj;
    }

    public void setProfileObj(ProfileObj profileObj) {
        this.profileObj = profileObj;
    }

    public TokenObj getTokenObj() {
        return tokenObj;
    }

    public void setTokenObj(TokenObj tokenObj) {
        this.tokenObj = tokenObj;
    }
}
