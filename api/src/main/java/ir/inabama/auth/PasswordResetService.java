package ir.inabama.auth;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ir.inabama.exceptions.AppException;
import ir.inabama.exceptions.UserNotFoundException;
import ir.inabama.user.User;
import ir.inabama.user.UserRepository;
import ir.inabama.utils.EmailService;
import ir.inabama.utils.RandomCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class PasswordResetService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Value("${auth.password.reset-subject}")
    private String resetSubject;

    @Value("${auth.password.reset-base-url}")
    private String resetBaseUrl;

    private LoadingCache<String, Integer> cache;
    private LoadingCache<Integer, String> codes;

    @PostConstruct
    public void init() {
        CacheLoader<String, Integer> loader = new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String s) {
                return 0;
            }
        };

        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build(loader);

        codes = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS)
                .build(new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer code) {
                        return null;
                    }
                });
    }

    public ResetPasswordType request(String username) throws UserNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (!byUsername.isPresent())
            throw new UserNotFoundException(username);

        User user = byUsername.get();
        int newCode;
        while (true) {
            newCode = RandomCodeGenerator.createNewCode();
            String un = codes.getIfPresent(newCode);
            if (un == null) {
                codes.put(newCode, username);
                break;
            }
        }

        // TODO fixme: enable sms service to authenticate user
        if (user.getMobileConfirmed()) {
            cache.put(username, newCode);
            //smsService.send(Long.parseLong(user.getMobile()), newCode + "");
            return ResetPasswordType.mobile();
        } else {
            cache.put(username, newCode);
            String link = resetBaseUrl + "?code=" + newCode;
            String text = "To reset click on \n" + link;
            emailService.sendEmail(user.getEmail(), resetSubject, text);
            return ResetPasswordType.email();
        }
    }

    public String findUsername(PasswordReset passwordReset) throws AppException {
        String username = codes.getIfPresent(passwordReset.getCode());
        if (username == null)
            throw new AppException("user not found for code: " + passwordReset.getCode());

        Integer code = cache.getIfPresent(username);
        if (code == null || !code.equals(passwordReset.getCode())) {
            throw new AppException("code expired, try again");
        }

        return username;
    }
}
