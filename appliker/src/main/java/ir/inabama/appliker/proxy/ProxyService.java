package ir.inabama.appliker.proxy;

import ir.inabama.appliker.account.Account;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import sun.net.SocksProxy;

import java.net.Proxy;

@Service
public class ProxyService {

	public OkHttpClient getProxy(Account account) {
		Proxy proxy = SocksProxy.NO_PROXY;
		return new OkHttpClient.Builder().proxy(proxy).build();
	}
}
