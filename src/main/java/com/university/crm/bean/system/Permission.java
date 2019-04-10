package com.university.crm.bean.system;

import com.university.crm.annotation.FieldNote;
import com.university.crm.enums.FieldEnum;

import java.util.Date;

/**
 * date: 2019/3/17
 * description : 权限
 *
 * @author : zhencai.cheng
 */
public class Permission {

    private Integer permissionID;
    /**
     * 权限名称
     */
    @FieldNote(name = "权限名称", type = FieldEnum.STRING, isNullAble = false)
    private String permissionName;
    /**
     * 权限状态
     */
    private String permissionState;
    /**
     * 权限标识
     */
    @FieldNote(name = "权限标识", type = FieldEnum.STRING, isNullAble = false)
    private String permissionSign;
    /**
     * 是否是菜单
     */
    @FieldNote(name = "是否是菜单", type = FieldEnum.NUMBER, isNullAble = false)
    private Integer menuItem;
    /**
     * 菜单url
     */
    private String menuUrl;
    /**
     * 父菜单ID
     */
    private Integer parentMenuId;
    /**
     * 菜单位置
     */
    private Integer menuSort;
    /**
     * 是否记录日志
     */
    private Integer log;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;

    public Integer getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(Integer permissionID) {
        this.permissionID = permissionID;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionState() {
        return permissionState;
    }

    public void setPermissionState(String permissionState) {
        this.permissionState = permissionState;
    }

    public String getPermissionSign() {
        return permissionSign;
    }

    public void setPermissionSign(String permissionSign) {
        this.permissionSign = permissionSign;
    }

    public Integer getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(Integer menuItem) {
        this.menuItem = menuItem;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Integer parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public Integer getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(Integer menuSort) {
        this.menuSort = menuSort;
    }

    public Integer getLog() {
        return log;
    }

    public void setLog(Integer log) {
        this.log = log;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
