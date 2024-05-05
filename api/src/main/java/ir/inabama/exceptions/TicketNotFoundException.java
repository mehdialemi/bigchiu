package ir.inabama.exceptions;

public class TicketNotFoundException extends AppException {
    public TicketNotFoundException(Long ticketId) {
        super("Ticket id: " + ticketId + "not found.");
    }
}
