package org.xspring.tutorial.guava.ext.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.xspring.tutorial.guava.ext.parser.ExtBeanDefinitionParser;

/**
 * <p>Title: ExtNamespaceHandler</p>
 * <p>Description:</p>
 * <p>Copyright: Ruijie Co., Ltd. (c) 2018</p>
 * <p>@Author: chenzhian </p>
 * <p>@Date: 2018/8/15 下午4:24</p>
 */
public class ExtNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("dateformat", new ExtBeanDefinitionParser());
    }
}
