package ir.inabama.auth;

import com.google.common.base.Strings;
import ir.inabama.common.ApiResponse;
import ir.inabama.exceptions.AppException;
import ir.inabama.auth.google.GoogleLoginRequest;
import ir.inabama.auth.security.JwtTokenProvider;
import ir.inabama.auth.security.UserPrincipal;
import ir.inabama.exceptions.UserNotFoundException;
import ir.inabama.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private UserService userService;

    private JwtAuthenticationResponse createJwtForGoogle(String username, String googleId) {
        Set<SimpleGrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority(RoleName.ROLE_USER.name()));

        UserPrincipal userPrincipal = new UserPrincipal(username, googleId, authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null);
        return createJwtFromAuthentication(authentication);
    }

    private JwtAuthenticationResponse createJwt(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return createJwtFromAuthentication(authentication);
    }

    private JwtAuthenticationResponse createJwtFromAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt);
    }

    @PostMapping("/google")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody GoogleLoginRequest loginRequest) throws AppException {
        if (loginRequest.getProfileObj() == null) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Google login is not correct"),
                    HttpStatus.BAD_REQUEST);
        }

        logger.info("Received login request by google: " + loginRequest.getProfileObj().getEmail());

        // TODO call google api to confirm google login
        if (Strings.isNullOrEmpty(loginRequest.getProfileObj().getGoogleId())) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Google login is not correct"),
                    HttpStatus.BAD_REQUEST);
        }

        String email = loginRequest.getProfileObj().getEmail();
        Optional<User> optionalUser = userRepository.findByUsername(email);
        if (!optionalUser.isPresent()) {
            User user = new User(email);
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new AppException("User Role not set."));
            user.setRoles(Collections.singleton(userRole));
            user.setName(loginRequest.getProfileObj().getName());
            user.setEmail(loginRequest.getProfileObj().getEmail());
            userRepository.save(user);
        }
        return ResponseEntity.ok(createJwtForGoogle(email, loginRequest.getProfileObj().getGoogleId()));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        logger.info("Received login request by username: " + loginRequest.getUsername());
        return ResponseEntity.ok(createJwt(loginRequest.getUsername(), loginRequest.getPassword()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest)  {
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmedPassword())) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Password and confirmed one does not match"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Role userRole;
        try {
            userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new AppException("User Role not set."));
        } catch (AppException e) {
            return ResponseEntity.badRequest().build();
        }
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        return ResponseEntity.ok(createJwt(signUpRequest.getUsername(), signUpRequest.getPassword()));
    }

    @GetMapping("reset/request/{username}")
    public ResponseEntity<?> resetPassRequest(@NotNull @PathVariable("username") String username) throws UserNotFoundException {
        ResetPasswordType resetType = passwordResetService.request(username);
        return new ResponseEntity<>(resetType, HttpStatus.OK);
    }

    @PostMapping("reset/response")
    public ResponseEntity<?> resetResponse(@Valid @RequestBody PasswordReset passwordReset) {
        try {
            String username = passwordResetService.findUsername(passwordReset);
            userService.changeUserPassword(username, passwordReset.getPassword(), passwordReset.getConfirmedPassword());
            return ResponseEntity.ok().build();
        } catch (Throwable e) {
            return new ResponseEntity<>(ApiResponse.FAILED, HttpStatus.BAD_REQUEST);
        }
    }
}
