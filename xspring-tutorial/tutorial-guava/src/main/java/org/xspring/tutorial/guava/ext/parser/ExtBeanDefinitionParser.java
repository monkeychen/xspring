package org.xspring.tutorial.guava.ext.parser;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.xspring.tutorial.guava.ext.User;

import java.text.SimpleDateFormat;

/**
 * <p>Title: ExtBeanDefinitionParser</p>
 * <p>Description:</p>
 * <p>Copyright: Ruijie Co., Ltd. (c) 2018</p>
 * <p>@Author: chenzhian </p>
 * <p>@Date: 2018/8/15 下午4:27</p>
 */
public class ExtBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        String tagName = element.getTagName();
        if ("ext:dateformat".equals(tagName)) {
            return SimpleDateFormat.class;
        }
        if ("ext:user".equals(tagName)) {
            return User.class;
        }
        return Object.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String tagName = element.getTagName();
        if ("ext:dateformat".equals(tagName)) {
            parseDateFormat(element, builder);
        } else if ("ext:user".equals(tagName)) {
            parseUser(element, builder);
        }
    }

    private void parseDateFormat(Element element, BeanDefinitionBuilder builder) {
//        String pattern = element.getAttribute("pattern");
//        builder.addPropertyValue("pattern", pattern);

        String lenient = element.getAttribute("lenient");
        if (StringUtils.hasText(lenient)) {
            builder.addPropertyValue("lenient", Boolean.valueOf(lenient));
        }
    }

    private void parseUser(Element element, BeanDefinitionBuilder builder) {
        String name = element.getAttribute("name");
        builder.addPropertyValue("name", name);

        String email = element.getAttribute("email");
        if (StringUtils.hasText(email)) {
            builder.addPropertyValue("email", email);
        }
    }
}
