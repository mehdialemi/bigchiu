package ir.inabama.common;

import ir.inabama.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    public static String DEFAULT_ADMIN_USERNAME = "admin";
    public static String DEFAULT_ADMIN_PASSWORD = "asdf/1234";

    @Value("${admin.username:admin}")
    private String adminUsername;

    @Value("${admin.password:asdf/1234}")
    private String adminPassword;

    boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup)
            return;

        createRoleIfNotFound(RoleName.ROLE_USER);
        createRoleIfNotFound(RoleName.ROLE_OPERATOR);
        Role adminRole = createRoleIfNotFound(RoleName.ROLE_ADMIN);

        Optional<User> byUsername = userRepository.findByUsername(adminUsername);
        if (byUsername.isPresent())
            return;

        User user = new User(adminUsername);
        user.setPassword(passwordEncoder.encode(adminPassword));
        user.setRoles(Collections.singleton(adminRole));
        userRepository.save(user);
    }

    @Transactional
    public Role createRoleIfNotFound(RoleName roleName) {

        Optional<Role> role = roleRepository.findByName(roleName);
        return role.orElseGet(() -> roleRepository.save(new Role(roleName)));
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }
}
