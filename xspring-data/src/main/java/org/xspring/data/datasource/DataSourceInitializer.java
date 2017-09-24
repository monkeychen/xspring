package org.xspring.data.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Map;

/**
 * <p>Title:          </p>
 * <p>Description:    </p>
 * <p>Copyright: Copyright (c) 2017  </p>
 * <p>Create Time: 2017/9/3 下午7:47 by ChenZhian            </p>
 */
@Configurable
@PropertySource(
        value = {
                "classpath:/config/datasource.properties",
                "file:./config/datasource.properties",
        },
        ignoreResourceNotFound = true
)
public class DataSourceInitializer implements EnvironmentAware, ApplicationContextAware {

    private Environment environment;

    private ApplicationContext applicationContext;

    @Bean
    public String dummy() {
        return "dataSourceInitializer";
    }

    @Bean
    public DataSource dataSource() {
        DynamicDataSource dataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSourceMap = Maps.newHashMap();
        DruidDataSource defaultDataSource = null;
        // TODO 解析datasource.properties文件中与数据源有关的配置项并初始化DruidDataSource

        DruidDataSource druidDataSource = new DruidDataSource();

        dataSource.setTargetDataSources(targetDataSourceMap);
        dataSource.setDefaultTargetDataSource(defaultDataSource);
        return dataSource;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
