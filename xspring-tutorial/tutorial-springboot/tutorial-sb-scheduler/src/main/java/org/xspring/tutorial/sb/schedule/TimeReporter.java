package org.xspring.tutorial.sb.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>Title:          </p>
 * <p>Description:    </p>
 * <p>Copyright: Copyright (c) 2017  </p>
 * <p>Create Time: 2017/9/5 下午2:16 by ChenZhian            </p>
 */
@Component
public class TimeReporter {
    private static final Logger logger = LoggerFactory.getLogger(TimeReporter.class);

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        logger.info("The time is now {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
