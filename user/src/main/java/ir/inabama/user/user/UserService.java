package ir.inabama.user.user;

import ir.inabama.user.exceptions.EmailNotFoundException;
import ir.inabama.user.exceptions.UserAlreadyExistsException;
import ir.inabama.user.exceptions.UsernameNotFoundException;
import ir.inabama.user.role.Role;
import jdk.internal.joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.openjpa.util.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private static final User EMPTY_USER = new User();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    public User createUser(String email, Role role) throws UserAlreadyExistsException {
        return createUser(email, null, role);
    }

    public User createUser(String email, String password, Role role) throws UserAlreadyExistsException {
        if (repository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        } else {
            User user = new User();
            user.setEmail(email);
            user.setUsername(email);

            if (!Strings.isNullOrEmpty(password))
                user.setPassword(passwordEncoder.encode(password));

            user.setRoles(Collections.singleton(role));
            return repository.save(user);
        }
    }

    public boolean exists(String email) {
        return repository.existsByEmail(email);
    }

    public void update(User value) throws UsernameNotFoundException {
        User user = getByUsername(value.getUsername());

        if (!Strings.isNullOrEmpty(value.getName()))
            user.setName(value.getName());

        if (!Strings.isNullOrEmpty(value.getAddress()))
            user.setAddress(value.getAddress());

        if (!Strings.isNullOrEmpty(value.getEmail()))
            user.setEmail(value.getEmail());

        if (!Strings.isNullOrEmpty(value.getMobile()))
            user.setMobile(value.getMobile());

        if (!Strings.isNullOrEmpty(value.getNationalCode()))
            user.setNationalCode(value.getNationalCode());

        if (!Strings.isNullOrEmpty(value.getPhone()))
            user.setPhone(value.getPhone());

        if (!Strings.isNullOrEmpty(value.getRegisterImageUrl()))
            user.setRegisterImageUrl(value.getRegisterImageUrl());

        if (value.getRoles().size() > 0)
            user.setRoles(value.getRoles());

        repository.save(user);
    }


    public User getByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = repository.findByUsername(username);
        return optional.orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public User getByEmail(String email) throws EmailNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException(email));
    }

    public User getByUsernameOrEmail(String username, String email) {
        return repository.findByUsernameOrEmail(username, email).orElseThrow(UserException::new);
    }
}
