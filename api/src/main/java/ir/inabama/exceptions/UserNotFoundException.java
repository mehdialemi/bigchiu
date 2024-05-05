package ir.inabama.exceptions;

public class UserNotFoundException extends AppException {

    public UserNotFoundException(String username) {
        super("username: " + username + " not found");
    }

    public UserNotFoundException(Long userId) {
        super("user id: " + userId + " not found");
    }
}
