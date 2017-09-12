package org.xspring.tutorial.sb.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>Title:          </p>
 * <p>Description:    </p>
 * <p>Copyright: Copyright (c) 2017  </p>
 * <p>Create Time: 2017/9/5 下午2:48 by ChenZhian            </p>
 */
@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
