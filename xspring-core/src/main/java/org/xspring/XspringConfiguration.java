package org.xspring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

/**
 * <p>Title: XspringConfiguration框架配置类         </p>
 * <p>Description: 框架启动时按如下顺序加载配置文件
 * <ol>
 *     <li>file:./config/env.properties</li>
 *     <li>classpath:/config/env.properties</li>
 *     <li>file:./config/xspring.properties</li>
 *     <li>file:./xspring.properties</li>
 *     <li>classpath:./config/xspring.properties</li>
 *     <li>classpath:./xspring.properties</li>
 * </ol>
 * </p>
 * <p>Copyright: Copyright (c) 2017  </p>
 * <p>Create Time: 2017/9/11 下午2:08 by ChenZhian            </p>
 */
@Configuration
@ComponentScan("org.xspring")
@EnableAspectJAutoProxy
@PropertySource(
        value = {
                "classpath:/xspring.properties",
                "classpath:/config/xspring.properties",
                "file:./xspring.properties",
                "file:./config/xspring.properties",
                "classpath:/config/env.properties",
                "file:./config/env.properties",
        },
        ignoreResourceNotFound = true
)
public class XspringConfiguration implements BeanFactoryAware {

    private ListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }
}
