package ir.inabama.exceptions;

public class NoVideoFoundException extends AppException {

    public NoVideoFoundException(String campaignTitle) {
        super("no video found for campaign title " + campaignTitle);
    }
}
