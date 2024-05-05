package ir.inabama.common;

import ir.inabama.user.Role;
import ir.inabama.user.RoleName;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ir.inabama.user.RoleName.*;

public class RoleUtils {

    public static RoleName detectRole(Set<Role> roles) {
        List<RoleName> roleNames = new ArrayList<>();
        for (Role role : roles) {
            roleNames.add(role.getName());
        }

        if (roleNames.contains(ROLE_ADMIN))
            return ROLE_ADMIN;

        if (roleNames.contains(ROLE_OPERATOR))
            return ROLE_OPERATOR;

        if (roleNames.contains(ROLE_USER))
            return ROLE_USER;

        return null;
    }

    public static boolean isUser(Set<Role> roles) {
        RoleName roleName = detectRole(roles);
        if (roleName.equals(ROLE_USER))
            return true;
        return false;
    }
}
