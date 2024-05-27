package com.cyx.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义防重提交.
 *
 * @author ChengYX
 * @version 1.0.0, 2024/5/26
 * @since 2024/5/26
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatSubmit {

    /**
     * 防重提交方式.
     * PARAM-方法参数，TOKEN-令牌.
     */
    enum Type {PARAM, TOKEN}

    /**
     * 默认防重提交（方法参数）.
     *
     * @return
     */
    Type limitType() default Type.PARAM;

    /**
     * 加锁过期时间,默认为5秒.
     * @return
     */
    long lockTime() default 5;
}
