package ir.bigchiu.user.google;

import ir.bigchiu.user.common.UserPrincipal;
import ir.bigchiu.user.common.UserPrincipalService;
import ir.bigchiu.user.exceptions.AuthException;
import ir.bigchiu.user.exceptions.EmailNotFoundException;
import ir.bigchiu.user.exceptions.UserAlreadyExistsException;
import ir.bigchiu.user.jwt.JwtService;
import ir.bigchiu.user.jwt.JwtToken;
import ir.bigchiu.user.role.RoleName;
import ir.bigchiu.user.role.RoleService;
import ir.bigchiu.user.user.User;
import ir.bigchiu.user.user.UserService;
import jdk.internal.joptsimple.internal.Strings;
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
