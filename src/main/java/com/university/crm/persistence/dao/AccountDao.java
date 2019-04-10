package com.university.crm.persistence.dao;

import com.university.crm.bean.system.Account;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * date: 2019/3/17
 * description :
 *
 * @author : zhencai.cheng
 */
@Component
public class AccountDao {

    private final String statementNamespace = "Account";
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public AccountDao(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Cacheable(value = "AccountCache", key = "#account")
    public Account findByAccount(String account) {
        return sqlSessionTemplate.selectOne(statementNamespace + ".findByAccount", account);
    }

    @CacheEvict(value = "AccountCache", key = "account.account")
    public void update(Account account) {
        sqlSessionTemplate.update(statementNamespace + ".update", account);
    }

    public void insertAccount(Account account) {
        sqlSessionTemplate.insert(statementNamespace + ".insertAccount", account);
    }

    public Account findByAccountID(Integer accountID) {
        return sqlSessionTemplate.selectOne(statementNamespace + ".findByAccountID", accountID);
    }
}
