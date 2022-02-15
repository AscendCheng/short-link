package com.cyx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.controller.request.UserLoginRequest;
import com.cyx.controller.request.UserRegisterRequest;
import com.cyx.enums.AuthType;
import com.cyx.enums.BizCodeEnum;
import com.cyx.enums.SendCodeEnum;
import com.cyx.manager.AccountManager;
import com.cyx.mapper.AccountMapper;
import com.cyx.model.AccountDO;
import com.cyx.model.LoginUser;
import com.cyx.service.AccountService;
import com.cyx.service.NotifyService;
import com.cyx.utils.CommonUtil;
import com.cyx.utils.IdUtil;
import com.cyx.utils.JsonData;
import com.cyx.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cyx
 * @since 2021-12-04
 */
@Service
@Slf4j
public class AccountServiceImpl extends ServiceImpl<AccountMapper, AccountDO> implements AccountService {
    @Autowired
    private NotifyService notifyService;

    @Autowired
    private AccountManager accountManager;

    /**
     * 1.手机验证码
     * 2.密码加密
     * 3.账号唯一性检查
     * 4.插入数据库
     * 5.新用户福利发放
     *
     * @param request 入参
     * @return
     */
    @Override
    public JsonData register(UserRegisterRequest request) {
        boolean checkCode = false;
        if (StringUtils.isNotBlank(request.getPhone())) {
            checkCode = notifyService.checkCode(SendCodeEnum.USER_REGISTER, request.getCode(), request.getPhone());
        }
        if (!checkCode) {
            return JsonData.buildResult(BizCodeEnum.CODE_ERROR);
        }

        AccountDO accountDO = new AccountDO();
        BeanUtils.copyProperties(request, accountDO);
        accountDO.setAccountNo(Long.valueOf(IdUtil.geneSnowFlakeID().toString()));
        // 认证级别
        accountDO.setAuth(AuthType.DEFAULT.name());

        // 密钥 盐
        accountDO.setSecret("$1$" + CommonUtil.getStringNumRandom(8));

        String cryptPwd = Md5Crypt.md5Crypt(accountDO.getPwd().getBytes(StandardCharsets.UTF_8), accountDO.getSecret());
        accountDO.setPwd(cryptPwd);

        int rows = accountManager.insert(accountDO);

        log.info("register insert rows:{},success：{}", rows, accountDO);

        // 用户注册福利，发放福利

        return JsonData.buildSuccess();
    }

    /**
     * 1.查询手机号
     * 2.通过密钥加密密码
     * 3.对比密文
     *
     * @param request
     * @return
     */
    @Override
    public JsonData login(UserLoginRequest request) {
        AccountDO accountDO = accountManager.findByPhone(request.getPhone());
        if (accountDO == null) {
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_UNREGISTER);
        }
        String cryptPwd = Md5Crypt.md5Crypt(request.getPwd().getBytes(StandardCharsets.UTF_8), accountDO.getSecret());
        if (!accountDO.getPwd().equalsIgnoreCase(cryptPwd)) {
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_PWD_ERROR);
        }

        LoginUser loginUser = LoginUser.builder().build();
        BeanUtils.copyProperties(accountDO, loginUser);
        return JsonData.buildSuccess(JwtUtil.generateJsonWebToken(loginUser));
    }
}
