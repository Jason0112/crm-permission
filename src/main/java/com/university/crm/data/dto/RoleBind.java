package com.university.crm.data.dto;

import com.university.crm.annotation.FieldNote;
import com.university.crm.enums.FieldEnum;

import java.util.List;

/**
 * date: 2019/3/18
 * description : 角色绑定权限
 *
 * @author : zhencai.cheng
 */
public class RoleBind {

    @FieldNote(name = "角色ID", type = FieldEnum.STRING, isNullAble = false)
    private  Integer roleID;

    @FieldNote(name = "权限ID", type = FieldEnum.COLLECTION, isNullAble = false)
    private List<Integer> permissionIDs;

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public List<Integer> getPermissionIDs() {
        return permissionIDs;
    }

    public void setPermissionIDs(List<Integer> permissionIDs) {
        this.permissionIDs = permissionIDs;
    }
}
