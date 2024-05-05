package ir.inabama.exceptions;

public class AdRequestNotFoundException extends AppException {
    public AdRequestNotFoundException() {
        super("invalid ad request");
    }
}
