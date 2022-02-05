package ir.inabama.user.auth;

import ir.inabama.user.google.GoogleService;
import ir.inabama.user.google.GoogleLogin;
import ir.inabama.user.exceptions.UserException;
import ir.inabama.user.exceptions.UsernameNotFoundException;
import ir.inabama.user.jwt.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

	@Autowired
	private AuthService internal;


	private GoogleService google;

	@PostMapping("/internal")
	public JwtToken authenticate(@Valid @RequestBody LoginRequest request) {
		return internal.auth(request.getUsername(), request.getPassword());
	}

	@PostMapping("/register")
	public JwtToken register(@Valid @RequestBody LoginRequest request) throws UserException, RoleNotFoundException {
		return internal.register(request.getEmail(), request.getPassword());
	}

	@GetMapping("/reset/password/{username}")
	public void resetPassword(@NotNull @PathVariable("username") String username) throws UsernameNotFoundException {
		internal.reset(username);
	}

	@PostMapping("/google")
	public JwtToken googleAuth(@Valid @RequestBody GoogleLogin request) throws UserException, RoleNotFoundException {
		return google.register(request);
	}
}
