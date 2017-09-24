package org.xspring;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.ClassUtils;
import org.xspring.core.extension.ModuleConfiguration;

import java.util.List;

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
        // Load other configurations in spring.factories file
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        List<String> factoryNames = SpringFactoriesLoader.loadFactoryNames(ModuleConfiguration.class, classLoader);
        List<Class> moduleConfigClasses = Lists.newArrayList();
        moduleConfigClasses.add(XspringConfiguration.class);
        if (CollectionUtils.isNotEmpty(factoryNames)) {
            for (String factoryName : factoryNames) {
                try {
                    Class factoryClass = ClassUtils.forName(factoryName, classLoader);
                    moduleConfigClasses.add(factoryClass);
                } catch (ClassNotFoundException e) {
                    logger.warn("Can not find the matched class[{}] in classpath!", factoryName);
                }
            }
        }
        if (annotatedClass != null) {
            moduleConfigClasses.add(annotatedClass);
        }
        Class[] configurations = moduleConfigClasses.toArray(new Class[moduleConfigClasses.size()]);
        context = new AnnotationConfigApplicationContext(configurations);
        context.start();
        return context;
    }
}
