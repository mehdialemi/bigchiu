package ir.inabama.appliker.media;

public class MediaNotFoundException extends Exception {

	public MediaNotFoundException(String id) {
		super("media id not found, id: " + id);
	}
}
