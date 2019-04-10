package com.university.crm.bean.system;

import com.university.crm.annotation.FieldNote;
import com.university.crm.enums.FieldEnum;

import java.util.Date;

/**
 * date: 2019/3/17
 * description : 菜单
 *
 * @author : zhencai.cheng
 */
public class Menu {

    private Integer menuID;
    /**
     * 菜单名称
     */
    @FieldNote(name = "菜单名称", type = FieldEnum.STRING, isNullAble = false)
    private String menuName;
    /**
     * 系统标识
     */
    private String systemSign;
    /**
     * 菜单排序位置
     */
    private Integer menuOrder;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;

    public Integer getMenuID() {
        return menuID;
    }

    public void setMenuID(Integer menuID) {
        this.menuID = menuID;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getSystemSign() {
        return systemSign;
    }

    public void setSystemSign(String systemSign) {
        this.systemSign = systemSign;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
