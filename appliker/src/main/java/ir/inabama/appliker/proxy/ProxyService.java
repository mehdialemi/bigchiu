package ir.inabama.appliker.proxy;

import ir.inabama.appliker.account.Account;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;

import java.net.Proxy;

@Service
public class ProxyService {

	public OkHttpClient getProxy(Account account) {
		return new OkHttpClient.Builder().proxy(Proxy.NO_PROXY).build();
	}
}
