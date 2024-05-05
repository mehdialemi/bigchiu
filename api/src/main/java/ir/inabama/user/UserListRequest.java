package ir.inabama.user;

import ir.inabama.common.ListRequest;

import java.util.List;

public class UserListRequest extends ListRequest {
    private List<RoleName> roleFilters;

    public List<RoleName> getRoleFilters() {
        return roleFilters;
    }

    public void setRoleFilters(List<RoleName> roleFilters) {
        this.roleFilters = roleFilters;
    }
}
