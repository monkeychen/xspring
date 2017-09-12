package org.xspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <p>Title:          </p>
 * <p>Description:    </p>
 * <p>Copyright: Copyright (c) 2017  </p>
 * <p>Create Time: 2017/9/8 下午9:48 by ChenZhian            </p>
 */
public class XspringApplication {
    private static final Logger logger = LoggerFactory.getLogger(XspringApplication.class);

    public static ConfigurableApplicationContext startup(Class<?> annotatedClass, String[] args) {
        return new XspringApplication().run(annotatedClass, args);
    }

    public ConfigurableApplicationContext run(Class<?> annotatedClass, String[] args) {
        logger.debug("The input arguments is:{}, {}", annotatedClass, args);
        AnnotationConfigApplicationContext context = null;
        if (annotatedClass == null) {
            context = new AnnotationConfigApplicationContext(XspringConfiguration.class);
        } else {
            context = new AnnotationConfigApplicationContext(XspringConfiguration.class, annotatedClass);
        }
        context.start();
        return context;
    }

    public static void main(String[] args) {
        startup(null, args);
    }
}
