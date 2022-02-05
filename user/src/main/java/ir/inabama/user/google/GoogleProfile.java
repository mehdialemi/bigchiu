package ir.inabama.user.google;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;

@Data
public class GoogleProfile {

    @Email
    private String email;
    private String familyName;
    private String givenName;
    private String googleId;

    @URL
    private String imageUrl;
    private String name;
}