package com.university.crm.data.dto;

import com.university.crm.annotation.FieldNote;
import com.university.crm.enums.FieldEnum;

import java.util.List;

/**
 * date: 2019/3/27
 * description :
 *
 * @author : zhencai.cheng
 */
public class AccountBind {

    @FieldNote(name = "账号", type = FieldEnum.NUMBER, isNullAble = false)
    private Integer accountID;

    @FieldNote(name = "角色", type = FieldEnum.COLLECTION, isNullAble = false)
    private List<Integer> roleIDs;

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public List<Integer> getRoleIDs() {
        return roleIDs;
    }

    public void setRoleIDs(List<Integer> roleIDs) {
        this.roleIDs = roleIDs;
    }
}
