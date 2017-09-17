package org.xspring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * <p>Title: XspringConfiguration框架配置类         </p>
 * <p>Description: 框架启动时按如下顺序加载配置文件
 * <ol>
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
@PropertySource(
        value = {
                "classpath:/xspring.properties",
                "classpath:/config/xspring.properties",
                "file:./xspring.properties",
                "file:./config/xspring.properties",
        },
        ignoreResourceNotFound = true
)
public class XspringConfiguration implements BeanFactoryAware {

    @Autowired
    private Environment environment;

    private ListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }
}
