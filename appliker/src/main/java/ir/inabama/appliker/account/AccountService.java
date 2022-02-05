package ir.inabama.appliker.account;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.github.instagram4j.instagram4j.models.user.Profile;
import ir.inabama.appliker.media.MediaService;
import ir.inabama.appliker.proxy.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

	@Autowired
	private Client client;

	@Autowired
	private MediaService mediaService;

	@Autowired
	private AccountRepository repository;

	public void create(String username, String password) throws IGLoginException, AccountAlreadyExists {
		if (exists(username))
			throw new AccountAlreadyExists(username);

		Account account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		IGClient login = client.login(account);

		if (login.isLoggedIn()) {
			Profile profile = login.getSelfProfile();
			account.setPk(profile.getPk());
			account.setFullName(profile.getFull_name());
			repository.save(account);
		}
	}


	public Account getAccount(String username) throws AccountNotFoundException {
		Optional <Account> byUsername = repository.getByUsername(username);
		return byUsername.orElseThrow(AccountNotFoundException::new);
	}

	public boolean exists(String username) {
		try {
			getAccount(username);
		} catch (AccountNotFoundException e) {
			return true;
		}

		return false;
	}

}
