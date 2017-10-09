package org.xspring.data;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.xspring.XspringApplication;

import javax.sql.DataSource;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * <p>
 * <p>Author:ChenZhian </p>
 * <p>Create at: 2017/9/24 下午3:28</p>
 */
public class XspringDataConfigurationTest {

    private ApplicationContext applicationContext;

    @Test
    public void testLoadConfigs() throws Exception {
        applicationContext = XspringApplication.startup(null, null);
        String dummy = applicationContext.getBean("dummy", String.class);
        System.out.println(dummy);
        DataSource dataSource = applicationContext.getBean("dataSource", DataSource.class);
        Assert.assertNotNull(dataSource);
    }
}