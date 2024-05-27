package com.cyx.aspect;

import com.cyx.annotation.RepeatSubmit;
import com.cyx.component.LoginInterceptor;
import com.cyx.constant.RedisKey;
import com.cyx.enums.BizCodeEnum;
import com.cyx.exception.BizException;
import groovy.util.logging.Slf4j;
import jodd.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * RepeatSubmitAspect.
 *
 * @author ChengYX
 * @version 1.0.0, 2024/5/26
 * @since 2024/5/26
 */
@Aspect
@Component
@Slf4j
public class RepeatSubmitAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("@annotation(repeatSubmit)")
    public void pointCutNoRepeatSubmit(RepeatSubmit repeatSubmit){

    }

    @Around("pointCutNoRepeatSubmit(repeatSubmit)")
    public Object around(ProceedingJoinPoint joinPoint,RepeatSubmit repeatSubmit) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        boolean result = false;


        long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();
        String type = repeatSubmit.limitType().name();
        if(type.equalsIgnoreCase(RepeatSubmit.Type.PARAM.name())){

        }else {
            String requestToken = request.getHeader("request-token");
            if(StringUtil.isBlank(requestToken)){
                throw new BizException(BizCodeEnum.ORDER_CONFIRM_TOKEN_EQUAL_FAIL);
            }
            String key = String.format(RedisKey.SUBMIT_ORDER_TOKEN_KEY,accountNo,requestToken);
            result = redisTemplate.delete(key);
        }
        if(!result){
            throw new BizException(BizCodeEnum.ORDER_CONFIRM_REPEAT);
        }

        Object object = joinPoint.proceed();
        return object;
    }
}
