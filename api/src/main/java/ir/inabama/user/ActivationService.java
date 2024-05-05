package ir.inabama.user;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ir.inabama.utils.RandomCodeGenerator;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class ActivationService {

    LoadingCache<String, Integer> loadingCache;

    public ActivationService() {
        CacheLoader<String, Integer> loader = new CacheLoader <String, Integer>() {
            @Override
            public Integer load(String s) {
                return 0;
            }
        };

        loadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build(loader);
    }

    public Integer createActivationCode(String username) {
        int newCode = RandomCodeGenerator.createNewCode();
        loadingCache.put(username, newCode);
        return newCode;
    }

    public Integer getActivationCode(String username) throws ExecutionException {
        return loadingCache.get(username);
    }

}
