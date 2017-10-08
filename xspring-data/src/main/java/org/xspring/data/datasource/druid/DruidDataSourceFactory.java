package org.xspring.data.datasource.druid;

import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.xspring.data.datasource.spi.DataSourceFactory;

import javax.sql.DataSource;
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
    @Override
    public DataSource getDefaultDataSource() {
        return null;
    }

    @Override
    public Map<String, DataSource> loadOriginalDataSources(Environment environment) {
        return null;
    }
}
