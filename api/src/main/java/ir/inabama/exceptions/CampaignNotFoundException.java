package ir.inabama.exceptions;

public class CampaignNotFoundException extends AppException {
    public CampaignNotFoundException(Long id ) {
        super("campaign id: " + id + " not found.");
    }

    public CampaignNotFoundException(Long id, String message) {
        super("Campaign id: " + id + " , " +  message);
    }
}
