package com.cyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.controller.request.UserLoginRequest;
import com.cyx.controller.request.UserRegisterRequest;
import com.cyx.model.AccountDO;
import com.cyx.utils.JsonData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyx
 * @since 2021-12-04
 */
public interface AccountService extends IService<AccountDO> {

    JsonData register(UserRegisterRequest request);

    JsonData login(UserLoginRequest request);
}
