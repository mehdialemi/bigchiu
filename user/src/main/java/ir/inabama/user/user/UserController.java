package ir.inabama.user.user;

import ir.inabama.user.exceptions.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping("update")
    @PreAuthorize("hasRole('USER')")
    public void updateUser(@RequestBody User user) throws UsernameNotFoundException {
        userService.update(user);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','OPERATOR')")
    public User getUser(@RequestParam("username") String username ) throws UsernameNotFoundException {
        return userService.getByUsername(username);
    }
}
