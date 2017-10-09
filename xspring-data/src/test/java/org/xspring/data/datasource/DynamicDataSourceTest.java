package org.xspring.data.datasource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.xspring.XspringApplication;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * <p>
 * <p>Author:ChenZhian </p>
 * <p>Create at: 2017/10/9 下午9:01</p>
 */
@Configuration
@ImportResource("classpath:/config/xspring-context-test.xml")
public class DynamicDataSourceTest {
    private ApplicationContext applicationContext;

    private DemoService demoService;
    @Before
    public void setUp() throws Exception {
        applicationContext = XspringApplication.startup(DynamicDataSourceTest.class, null);
        demoService = applicationContext.getBean("demoService", DemoService.class);
    }

    @Test
    public void testDynamicDataSource() throws Exception {
        demoService.printClassroomList();
        demoService.printUserList();
    }
}