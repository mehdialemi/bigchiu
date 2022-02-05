package ir.inabama.appliker.proxy;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import ir.inabama.appliker.account.Account;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class Client {

	@Cacheable(key = "username")
	public IGClient login(Account account) throws IGLoginException {
		IGClient login = IGClient.builder()
				.username(account.getUsername())
				.password(account.getPassword())
				.login();

		return login;
	}

}
