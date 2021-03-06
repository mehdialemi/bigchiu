package ir.inabama.user.role;

import ir.inabama.user.exceptions.UsernameNotFoundException;
import ir.inabama.user.user.UserService;
import ir.inabama.user.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repository;

	@Autowired
	private UserService userService;

	public void add(String username, String roleName) throws UsernameNotFoundException, RoleNotFoundException {
		User user = userService.getByUsername(username);
		Role role = get(roleName);
		user.getRoles().add(role);
	}

	public void delete(String username, String roleName) throws UsernameNotFoundException, RoleNotFoundException {
		User user = userService.getByUsername(username);
		Role role = get(roleName);
		user.getRoles().remove(role);
	}

	public Role get(String roleName) throws RoleNotFoundException {
		return repository.findByName(RoleName.valueOf(roleName))
				.orElseThrow(() -> new RoleNotFoundException(roleName));

	}
}
