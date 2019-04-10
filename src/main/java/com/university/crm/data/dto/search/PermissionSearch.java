package com.university.crm.data.dto.search;

/**
 * date: 2019/3/18
 * description : 权限搜索条件
 *
 * @author : zhencai.cheng
 */
public class PermissionSearch {

    private String permissionName;

    private Integer permissionState;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Integer getPermissionState() {
        return permissionState;
    }

    public void setPermissionState(Integer permissionState) {
        this.permissionState = permissionState;
    }
}
