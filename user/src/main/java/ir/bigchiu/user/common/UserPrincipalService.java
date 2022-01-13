package ir.bigchiu.user.common;

import ir.bigchiu.user.role.RoleName;
import ir.bigchiu.user.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserPrincipalService {

    public UserPrincipal getUserPrincipal(String username, String password) {
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setUsername(username);
        userPrincipal.setPassword(password);
        userPrincipal.setAuthorities(Collections.singleton(new SimpleGrantedAuthority(RoleName.USER.name())));
        return userPrincipal;
    }
}
