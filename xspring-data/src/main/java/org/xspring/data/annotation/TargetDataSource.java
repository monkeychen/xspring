package org.xspring.data.annotation;

import java.lang.annotation.*;

/**
 * <p>Title:TargetDataSource注解          </p>
 * <p>Description: <br/>
 * 用于指定注解所标注的类或方法中业务逻辑所使用的目标数据源名  <br/>
 * This annotation was useSpecified target data source name which will be used in the whole class or method.
 * </p>
 * <p>Copyright: Copyright (c) 2017  </p>
 * <p>Create Time: 2017/8/5 下午10:46 by ChenZhian            </p>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String value();
}
