package org.xspring.core.annotation;

import org.xspring.core.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Title:          </p>
 * <p>Description:    </p>
 * <p>Copyright: Copyright (c) 2017  </p>
 * <p>Create Time: 2017/8/20 上午10:02 by ChenZhian            </p>
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBusDescription {
    /**
     * 总线名
     * @return name
     */
    String name() default Constants.DEFAULT_BUS_NAME;

    /**
     * 异步线程池个数,只有异步类型的事件总线该参数才生效
     * @return int
     */
    int threadNum() default 10;
}
