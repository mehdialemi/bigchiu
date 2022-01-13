package ir.bigchiu.user.auth;

import ir.bigchiu.user.exceptions.PasswordValidationException;
import ir.bigchiu.user.jwt.JwtService;
import ir.bigchiu.user.jwt.JwtToken;
import ir.bigchiu.user.activation.EmailService;
import ir.bigchiu.user.activation.SmsService;
import ir.bigchiu.user.exceptions.UserException;
import ir.bigchiu.user.exceptions.UsernameNotFoundException;
import ir.bigchiu.user.role.RoleName;
import ir.bigchiu.user.role.RoleService;
import ir.bigchiu.user.user.User;
import ir.bigchiu.user.user.UserService;
import jdk.internal.joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class AuthService {

	@Autowired
	private SmsService smsService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private JwtService jwtService;

	@Value("${auth.password.reset-subject}")
	private String resetSubject;

	@Value("${auth.password.reset-base-url}")
	private String resetBaseUrl;

	private HashMap <String, Integer> codes = new HashMap <>();

	public void reset(String username) throws UsernameNotFoundException {
		User user = userService.getByUsername(username);
		int newCode = ThreadLocalRandom.current().nextInt(10001, 99999);
		if (user.isConfirmed()) {
			codes.put(username, newCode);
			smsService.send(user.getMobile(), newCode);
		} else {
			codes.put(username, newCode);
			String link = resetBaseUrl + "?code=" + newCode;
			String text = "To reset click on \n" + link;
			emailService.send(user.getEmail(), resetSubject, text);
		}
	}

	public JwtToken auth(String username, String password) {
		log.info("Received login request by username: {}", username);
		return jwtService.createToken(username, password);
	}

	public JwtToken register(String email, String password) throws UserException, RoleNotFoundException {
		User user = userService.createUser(email, password, roleService.get(RoleName.USER.name()));
		return jwtService.createToken(user.getUsername(), password);
	}


	public void changePassword(String username, String password, String confirmed) throws PasswordValidationException, UsernameNotFoundException {
		if (Strings.isNullOrEmpty(password) || !password.equals(confirmed))
			throw new PasswordValidationException("password does not match");

		Optional<User> optional = repository.findByUsername(username);
		User user = optional.orElseThrow(() -> new UsernameNotFoundException(username));
		user.setPassword(passwordEncoder.encode(password));
	}

}
