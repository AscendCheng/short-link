package com.cyx.manager;

import com.cyx.model.AccountDO;

public interface AccountManager {
    /**
     * 插入账号
     *
     * @param accountDO
     * @return
     */
    int insert(AccountDO accountDO);

    /**
     * 通过手机查找账号
     *
     * @param phone
     * @return
     */
    AccountDO findByPhone(String phone);
}
