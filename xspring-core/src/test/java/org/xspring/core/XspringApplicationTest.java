package org.xspring.core;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xspring.XspringConfiguration;

/**
 * <p>Title:          </p>
 * <p>Description:    </p>
 * <p>Copyright: Copyright (c) 2017  </p>
 * <p>Create Time: 2017/9/8 下午10:45 by ChenZhian            </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={XspringConfiguration.class})
public class XspringApplicationTest {
    private static final Logger logger = LoggerFactory.getLogger(XspringApplicationTest.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testStartup() throws Exception {
    }
}