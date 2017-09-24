package org.xspring.data;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.xspring.core.extension.ModuleConfiguration;
import org.xspring.data.datasource.DataSourceInitializer;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * <p>
 * <p>Author:ChenZhian </p>
 * <p>Create at: 2017/9/17 下午9:12</p>
 */
@Configuration
@Import(DataSourceInitializer.class)
public class XspringDataConfiguration implements BeanFactoryAware, ModuleConfiguration {

    private ListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }
}
