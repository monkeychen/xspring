package org.xspring.data.datasource;

import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.xspring.data.datasource.spi.DataSourceFactory;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:          </p>
 * <p>Description:    </p>
 * <p>Copyright: Copyright (c) 2017  </p>
 * <p>Create Time: 2017/9/3 下午7:47 by ChenZhian            </p>
 */
@Configuration
@PropertySource(
        value = {
                "classpath:/config/datasource.properties",
                "file:./config/datasource.properties",
        },
        ignoreResourceNotFound = true
)
public class DataSourceInitializer implements EnvironmentAware, BeanFactoryAware {

    private Environment environment;

    private DefaultListableBeanFactory beanFactory;

    @Bean
    public String dummy() {
        return "dataSourceInitializer";
    }

    @Bean
    public DataSource dataSource() {
        // 加载DataSourceFactory实现类列表，返回的实例已根据@order注解进行升序排序
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        List<DataSourceFactory> dataSourceFactories = SpringFactoriesLoader.loadFactories(DataSourceFactory.class, classLoader);
        if (CollectionUtils.isEmpty(dataSourceFactories)) {
            throw new BeanCreationException("Not found any DataSourceFactory implementer in class path!");
        }

        DataSourceFactory dataSourceFactory = dataSourceFactories.get(0);
        Map<String, DataSource> dataSourceMap = dataSourceFactory.loadOriginalDataSources(environment);
        if (CollectionUtils.isEmpty(dataSourceMap)) {
            throw new BeanCreationException("Fail to load any original DataSource instance!");
        }

        Map<Object, Object> targetDataSourceMap = Maps.newHashMap();
        dataSourceMap.forEach((name, dataSource) -> {
            beanFactory.registerSingleton(name, dataSource); // 将具体的DataSource实例注册进ApplicationContext
            targetDataSourceMap.put(name, dataSource);
            DynamicDataSourceContextHolder.addDataSourceId(name);
        });
        DataSource defaultDataSource = dataSourceFactory.getDefaultDataSource(environment);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSourceMap);
        dataSource.setDefaultTargetDataSource(defaultDataSource);
        return dataSource;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }
}
