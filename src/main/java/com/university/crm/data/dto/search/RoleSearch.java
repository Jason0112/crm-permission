package com.university.crm.data.dto.search;

/**
 * date: 2019/4/1
 * description :
 *
 * @author : zhencai.cheng
 */
public class RoleSearch {

    private String roleName;

    private Integer roleState;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleState() {
        return roleState;
    }

    public void setRoleState(Integer roleState) {
        this.roleState = roleState;
    }
}
