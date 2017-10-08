package org.xspring.data.datasource.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.xspring.data.datasource.spi.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

/**
 * <p>Title:DruidDataSourceFactory</p>
 * <p>Description:框架默认提供的数据库连接池：Druid数据源连接池</p>
 * <p>
 * <p>Author:ChenZhian </p>
 * <p>Create at: 2017/10/8 下午8:44</p>
 */
@Order
public class DruidDataSourceFactory implements DataSourceFactory {
    private static final Logger logger = LoggerFactory.getLogger(DruidDataSourceFactory.class);

    private static final String DATA_SOURCE_KEY_PREFIX = "xspring.datasource.jdbc.";
    private static final String DEFAULT_DATA_SOURCE_NUM = DATA_SOURCE_KEY_PREFIX + "default";
    private static final String MAX_DATA_SOURCE_NUM = DATA_SOURCE_KEY_PREFIX + "max";
    private static final String KEY_DS_NAME = "name";
    private static final String KEY_DS_DRIVER_CLASS_NAME = "driverClassName";
    private static final String KEY_DS_URL = "url";
    private static final String KEY_DS_USERNAME = "username";
    private static final String KEY_DS_PASSWORD = "password";
    private static final String KEY_DS_INIT_POOL_SIZE = "initialSize";
    private static final String KEY_DS_MIN_POOL_SIZE = "minPoolSize";
    private static final String KEY_DS_MAX_POOL_SIZE = "maxPoolSize";
    private static final String KEY_DS_POOL_FILTERS = "poolFilters";

    private DataSource defaultDataSource;

    @Override
    public synchronized DataSource getDefaultDataSource(Environment environment) {
        if (defaultDataSource == null) {
            Integer defDataSourceNum = environment.getProperty(DEFAULT_DATA_SOURCE_NUM, Integer.class, 1);
            String name = getProperty(environment, defDataSourceNum, KEY_DS_NAME);
            if (Strings.isNullOrEmpty(name)) {
                logger.warn("Fail to find the [{}]th data source name in environment!", defDataSourceNum);
                return null;
            }
            defaultDataSource = newDruidDataSource(environment, defDataSourceNum);
        }
        return defaultDataSource;
    }

    @Override
    public synchronized Map<String, DataSource> loadOriginalDataSources(Environment environment) {
        Map<String, DataSource> dataSourceMap = Maps.newHashMap();
        Integer maxDataSourceNum = environment.getProperty(MAX_DATA_SOURCE_NUM, Integer.class, 1);
        Integer defDataSourceNum = environment.getProperty(DEFAULT_DATA_SOURCE_NUM, Integer.class, 1);
        for (int i = 1; i <= maxDataSourceNum; i++) {
            String name = getProperty(environment, i, KEY_DS_NAME);
            if (Strings.isNullOrEmpty(name)) {
                logger.warn("Fail to find the [{}]th data source name[{}] in environment!", i, name);
                continue;
            }
            DruidDataSource dataSource = newDruidDataSource(environment, i);
            dataSourceMap.put(name, dataSource);
            if (defDataSourceNum.equals(i)) {
                this.defaultDataSource = dataSource;
            }
            try {
                dataSource.init();
            } catch (SQLException e) {
                logger.error("Fail to initialize druid data source[name={}]!", name, e);
            }
        }
        return dataSourceMap;
    }

    private DruidDataSource newDruidDataSource(Environment environment, int i) {
        String driverClassName = getProperty(environment, i, KEY_DS_DRIVER_CLASS_NAME);
        String url = getProperty(environment, i, KEY_DS_URL);
        String username = getProperty(environment, i, KEY_DS_USERNAME);
        String password = getProperty(environment, i, KEY_DS_PASSWORD);
        Integer initialSize = getProperty(environment, i, KEY_DS_INIT_POOL_SIZE, Integer.class, 10);
        Integer minPoolSize = getProperty(environment, i, KEY_DS_MIN_POOL_SIZE, Integer.class, 5);
        Integer maxPoolSize = getProperty(environment, i, KEY_DS_MAX_POOL_SIZE, Integer.class, 10);
        String poolFilters = getProperty(environment, i, KEY_DS_POOL_FILTERS);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minPoolSize);
        dataSource.setMaxActive(maxPoolSize);
        try {
            dataSource.setFilters(poolFilters);
        } catch (SQLException e) {
            logger.error("Fail to set druid data source's filters property to specified value[{}]!", poolFilters, e);
        }
        return dataSource;
    }

    private  <T> T getProperty(Environment environment, int num, String key, Class<T> targetType, T defaultValue) {
        String targetKey = DATA_SOURCE_KEY_PREFIX + num + "." + key;
        return environment.getProperty(targetKey, targetType, defaultValue);
    }

    private String getProperty(Environment environment, int num, String key) {
        String targetKey = DATA_SOURCE_KEY_PREFIX + num + "." + key;
        return environment.getProperty(targetKey);
    }
}
