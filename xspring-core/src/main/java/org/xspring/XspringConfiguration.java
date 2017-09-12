package org.xspring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * <p>Title:          </p>
 * <p>Description:    </p>
 * <p>Copyright: Copyright (c) 2017  </p>
 * <p>Create Time: 2017/9/11 下午2:08 by ChenZhian            </p>
 */
@Configuration
@ComponentScan("org.xspring")
@PropertySource(value = "file:./config/xspring.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:./xspring.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:/config/xspring.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:/xspring.properties", ignoreResourceNotFound = true)
public class XspringConfiguration implements EnvironmentAware, BeanFactoryAware {

    private Environment environment;

    private ListableBeanFactory beanFactory;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }
}
