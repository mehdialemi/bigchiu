package ir.inabama.user.google;

import lombok.Data;

@Data
public class GoogleLogin {
    private GoogleProfile googleProfile;
    private GoogleToken googleToken;
}
