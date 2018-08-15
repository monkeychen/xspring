package org.xspring.tutorial.guava.ext.parser;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import java.text.SimpleDateFormat;

/**
 * <p>Title: ExtBeanDefinitionParser</p>
 * <p>Description:</p>
 * <p>Copyright: Ruijie Co., Ltd. (c) 2018</p>
 * <p>@Author: chenzhian </p>
 * <p>@Date: 2018/8/15 下午4:27</p>
 */
public class ExtBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return SimpleDateFormat.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
//        // this will never be null since the schema explicitly requires that a value be supplied
//        String pattern = element.getAttribute("pattern");
//        //builder.addConstructorArgValue(pattern);
//        builder.addPropertyValue("pattern", pattern);

        // this however is an optional property
        String lenient = element.getAttribute("lenient");
        if (StringUtils.hasText(lenient)) {
            builder.addPropertyValue("lenient", Boolean.valueOf(lenient));
        }
    }
}
