package ir.inabama.user.auth;

import ir.inabama.user.activation.EmailService;
import ir.inabama.user.activation.SmsService;
import ir.inabama.user.exceptions.UserException;
import ir.inabama.user.exceptions.UsernameNotFoundException;
import ir.inabama.user.jwt.JwtService;
import ir.inabama.user.jwt.JwtToken;
import ir.inabama.user.role.RoleName;
import ir.inabama.user.role.RoleService;
import ir.inabama.user.user.User;
import ir.inabama.user.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.HashMap;
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

	@Value("${auth.password.reset-subject:ChangePassword}")
	private String resetSubject;

	@Value("${auth.password.reset-base-url:https://localhost}")
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
}
