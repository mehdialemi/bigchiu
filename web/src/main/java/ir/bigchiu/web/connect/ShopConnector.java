package ir.bigchiu.web.connect;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@Slf4j
public class ShopConnector {

	private Retrofit retrofit;

	@PostConstruct
	public void init() {
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		this.retrofit = new Retrofit.Builder()
				.baseUrl("https://api.github.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.client(httpClient.build())
				.build();
	}

	public Retrofit getRetrofit() {
		return retrofit;
	}

	public <T> T execute(Call<T> call) {
		try {
			Response <T> response = call.execute();
			if (response.isSuccessful())
				return response.body();
			log.warn("Unsuccessful response from shop service, response: {}", response);
		} catch (IOException e) {
			log.error("Unable to call shop APIs", e);
		}

		throw new RuntimeException("Error in calling shop service");
	}
}
