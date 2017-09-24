package org.xspring.tutorial.sb.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>Title:          </p>
 * <p>Description:    </p>
 * <p>Copyright: Copyright (c) 2017  </p>
 * <p>Create Time: 2017/9/5 下午2:48 by ChenZhian            </p>
 */
@SpringBootApplication
@EnableScheduling
@PropertySource(value = "classpath:/config/scheduler.properties", ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "xspring.tutorial.sb.scheduler.p1")
public class Application {

    private String key1;

    private String key2;

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class);
        Application application = applicationContext.getBean("application", Application.class);
        System.out.println(application);
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }
}
