package ir.inabama.appliker.account;

public class AccountAlreadyExists extends Exception {

	public AccountAlreadyExists(String username) {
		super(username + " already exists");
	}
}
