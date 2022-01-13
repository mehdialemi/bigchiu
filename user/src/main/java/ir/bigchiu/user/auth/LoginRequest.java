package ir.bigchiu.user.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String username;
    private String password;
}
