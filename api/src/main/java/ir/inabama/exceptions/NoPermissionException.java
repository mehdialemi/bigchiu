package ir.inabama.exceptions;

public class NoPermissionException extends AppException {
    public NoPermissionException(String permission) {
        super("No permission to access " + permission);
    }
}
