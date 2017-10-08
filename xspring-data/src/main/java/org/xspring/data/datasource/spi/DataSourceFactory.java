package org.xspring.data.datasource.spi;

import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Map;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * <p>
 * <p>Author:ChenZhian </p>
 * <p>Create at: 2017/10/8 下午8:36</p>
 */
public interface DataSourceFactory {

    DataSource getDefaultDataSource(Environment environment);

    Map<String, DataSource> loadOriginalDataSources(Environment environment);
}
