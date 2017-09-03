package org.xspring.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Title: EventSubscriber Annotation</p>
 * <p>Description:事件观察者注解</p>
 * <p>
 * <p>Author:ChenZhian </p>
 * <p>Create at: 2017/8/18 17:02</p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventSubscriber {

    /**
     * 是否异步处理
     * @return boolean
     */
    boolean async() default false;

    /**
     * 事件总线描述信息
     * @return EventBusDescription
     */
    EventBusDescription description() default @EventBusDescription();
}
