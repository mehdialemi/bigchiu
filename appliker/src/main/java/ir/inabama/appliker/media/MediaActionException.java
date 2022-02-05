package ir.inabama.appliker.media;

public class MediaActionException extends Exception {

	public MediaActionException(String id, String status) {
		super("id: " + id + ", status: " + status);
	}
}
