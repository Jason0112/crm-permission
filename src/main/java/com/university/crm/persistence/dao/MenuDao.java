package com.university.crm.persistence.dao;

import com.university.crm.bean.system.Menu;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * date: 2019/3/18
 * description :
 *
 * @author : zhencai.cheng
 */
@Component
public class MenuDao {

    private final String statementNamespace = "Menu";
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public MenuDao(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public void insertMenu(Menu menu) {
        sqlSessionTemplate.insert(statementNamespace + ".insertMenu", menu);
    }

    public void updateMenu(Menu menu) {
        sqlSessionTemplate.update(statementNamespace + ".updateMenu", menu);
    }



    public void deleteMenu(Integer menuID) {
        sqlSessionTemplate.delete(statementNamespace + ".deleteMenu", menuID);
    }

    public Menu findMenuByID(Integer menuID) {
        return sqlSessionTemplate.selectOne(statementNamespace + ".findMenuByID", menuID);
    }
}
