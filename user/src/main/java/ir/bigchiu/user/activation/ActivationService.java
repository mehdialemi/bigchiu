package ir.bigchiu.user.activation;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class ActivationService {

    @Cacheable("otp")
    public Integer getActivationCode(String phoneNumber) {
        return ThreadLocalRandom.current().nextInt(9900) + 1000;
    }

}
