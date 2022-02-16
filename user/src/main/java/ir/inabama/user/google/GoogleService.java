package ir.inabama.user.google;

import com.google.common.base.Strings;
import ir.inabama.user.common.UserPrincipal;
import ir.inabama.user.common.UserPrincipalService;
import ir.inabama.user.exceptions.AuthException;
import ir.inabama.user.exceptions.EmailNotFoundException;
import ir.inabama.user.exceptions.UserAlreadyExistsException;
import ir.inabama.user.jwt.JwtService;
import ir.inabama.user.jwt.JwtToken;
import ir.inabama.user.role.RoleName;
import ir.inabama.user.role.RoleService;
import ir.inabama.user.user.User;
import ir.inabama.user.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;

@Service
@Slf4j
public class GoogleService {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserPrincipalService userPrincipalService;


	public JwtToken register(GoogleLogin request) throws AuthException, RoleNotFoundException, UserAlreadyExistsException {
		GoogleProfile profile = request.getGoogleProfile();
		log.info("Received login request by google: {}", profile.getEmail());

		if (Strings.isNullOrEmpty(profile.getGoogleId())) {
			throw new AuthException("Google login is not correct");
		}

		User user;
		try {
			user = userService.getByEmail(profile.getEmail());
		} catch (EmailNotFoundException e) {
			user = userService.createUser(profile.getEmail(), roleService.get(RoleName.USER.name()));
		}

		UserPrincipal userPrincipal = userPrincipalService.getUserPrincipal(user.getUsername(), profile.getGoogleId());
		return jwtService.createToken(new UsernamePasswordAuthenticationToken(userPrincipal, null));
	}
}
