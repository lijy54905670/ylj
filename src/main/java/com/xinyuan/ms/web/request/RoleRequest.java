package com.xinyuan.ms.web.request;

public class RoleRequest {

    public Long roleId;
    public String roleName;
    public String menuIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    @Override
    public String toString() {
        return "RoleRequest{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", menuIds='" + menuIds + '\'' +
                '}';
    }
}
