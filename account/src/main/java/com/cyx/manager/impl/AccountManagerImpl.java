package com.cyx.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyx.manager.AccountManager;
import com.cyx.mapper.AccountMapper;
import com.cyx.model.AccountDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountManagerImpl implements AccountManager {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public int insert(AccountDO accountDO) {
        return accountMapper.insert(accountDO);
    }

    @Override
    public AccountDO findByPhone(String phone) {
        if(phone == null){
            return null;
        }
        return accountMapper.selectOne(new QueryWrapper<AccountDO>().eq("phone", phone));
    }
}
