package ir.inabama.user;

import ir.inabama.common.ApiResponse;
import ir.inabama.exceptions.AppException;
import ir.inabama.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static ir.inabama.user.RoleName.ROLE_USER;

@Service
public class UserService {
    private static final User EMPTY_USER = new User();
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ActivationService activationService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    Pair<ApiResponse, User> updateProfile(UserProfile userProfile) {
        Optional<User> stored = userRepository.findByUsername(userProfile.getUsername());
        if (!stored.isPresent()) {
            return Pair.of(new ApiResponse(false, "نام کاربری یافت نشد"), EMPTY_USER);
        }

        User user = stored.get();
        user.update(userProfile);
        user = userRepository.saveAndFlush(user);

        return Pair.of(new ApiResponse(true, "اطلاعات با موفقیت بروز شد"), user);
    }

    User addRole(String username, String roleName) throws UserNotFoundException, RoleNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (!byUsername.isPresent())
            throw new UserNotFoundException(username);

        Role role;
        try {
            role = roleRepository.findByName(RoleName.valueOf(roleName)).get();
        } catch (Throwable e) {
            throw new RoleNotFoundException(roleName);
        }

        User user = byUsername.get();
        user.getRoles().add(role);

        return userRepository.save(user);
    }

    User deleteRole(String username, String roleName) throws UserNotFoundException, RoleNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (!byUsername.isPresent())
            throw new UserNotFoundException(username);

        Role role;
        try {
            role = roleRepository.findByName(RoleName.valueOf(roleName)).get();
        } catch (Throwable e) {
            throw new RoleNotFoundException(roleName);
        }

        User user = byUsername.get();
        user.getRoles().remove(role);

        return userRepository.save(user);
    }


    User changeUserPassword(String username, String password) throws AppException {
        return changeUserPassword(username, password, password);
    }

    public User changeUserPassword(String username, String password, String confirmedPassword)
            throws AppException {
        if (password == null || !password.equals(confirmedPassword))
            throw new AppException("password does not match");

        Optional<User> byUsername = userRepository.findByUsername(username);
        if (!byUsername.isPresent())
            throw new UserNotFoundException(username);

        User user = byUsername.get();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    Pair<ApiResponse, User> setFreeCredit(String username, long amount) {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (!byUsername.isPresent()) {
            return Pair.of(new ApiResponse(false, "کاربر یافت نشد"), EMPTY_USER);
        }

        User user = byUsername.get();
        user.setFreeCredit(amount);
        user = userRepository.save(user);
        return Pair.of(new ApiResponse(true, "اطلاعات با موفقیت بروز شد"), user);
    }

    Pair<ApiResponse, User> increaseBudget(String username, long amount) {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (!byUsername.isPresent()) {
            return Pair.of(new ApiResponse(false, "کاربر یافت نشد"), EMPTY_USER);
        }

        User user = byUsername.get();
        long freeCredit = user.getFreeCredit() + amount;
        user.setFreeCredit(freeCredit);
        user = userRepository.save(user);
        return Pair.of(new ApiResponse(true, "اطلاعات با موفقیت بروز شد"), user);
    }

    Pair<ApiResponse, User> decreaseBudget(String username, long amount) {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (!byUsername.isPresent()) {
            return Pair.of(new ApiResponse(false, "کاربر یافت نشد"), EMPTY_USER);
        }

        User user = byUsername.get();
        long freeCredit = user.getFreeCredit() - amount;
        user.setFreeCredit(freeCredit);
        user = userRepository.save(user);
        return Pair.of(new ApiResponse(true, "اطلاعات با موفقیت بروز شد"), user);
    }

    public Long getFreeCredit(String username) throws UserNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (!byUsername.isPresent()) {
            logger.warn("user not found: {}", username);
            throw new UserNotFoundException(username);
        }

        return byUsername.get().getFreeCredit();
    }

    public User get(String username) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent())
            throw new UserNotFoundException(username);

        return userOptional.get();
    }

    User get(Long id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent())
            throw new UserNotFoundException(id);

        return userOptional.get();
    }

    ApiResponse checkMobileActivationCode(String username, Integer code) {
        Integer activationKey = null;
        try {
            activationKey = activationService.getActivationCode(username);
        } catch (ExecutionException e) {
            return new ApiResponse(false, "مشکل در بازیابی کد بوجود آمده است. دوباره تلاش کنید");
        }

        if (activationKey == null || !activationKey.equals(code))
            return new ApiResponse(false, "کد ورودی صحیح نیست");
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (!byUsername.isPresent())
            return new ApiResponse(false, "نام کاربری یافت نشد");

        User user = byUsername.get();
        user.setMobileConfirmed(true);
        userRepository.save(user);
        return new ApiResponse(true, "کد مورد تایید است");
    }

    UserListResponse userList(UserListRequest request) {
        UserListResponse response = new UserListResponse();

        Pageable pageable = request.createPageabel();
        if (pageable == null) {
            response.setUserList(Collections.emptyList());
            response.setTotalPages(0L);
            return response;
        }

        List<String> roleFilter = new ArrayList<>();
        if ((request.getRoleFilters() != null) && (!request.getRoleFilters().isEmpty())) {
            request.getRoleFilters().forEach(user -> roleFilter.add(user.name()));
        } else {
            roleFilter.add(ROLE_USER.name());
        }

        Page<User> users = userRepository.findAllByFilterRoles(roleFilter, pageable);

        response.setTotalPages(users.getTotalElements());
        response.setUserList(users.getContent());

        return response;
    }
}
