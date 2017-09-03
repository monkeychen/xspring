package org.xspring.core.eventbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * <p>
 * <p>Author:ChenZhian </p>
 * <p>Create at: 2017/8/18 21:47</p>
 */
@Component
public class EventBusService implements ApplicationListener, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventBusService.class);

    private ApplicationContext context;

    private AtomicInteger refreshedEventCounter = new AtomicInteger(0);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            int cnt = refreshedEventCounter.incrementAndGet();
            logger.info("Received ContextRefreshedEvent, count = {}!", cnt);
            if (cnt > 1) { // 避免频繁加载
                return;
            }
            // 加载所有使用EventSubscriber注解的订阅者
            EventBusFactory.getInstance().registerEventSubscriber(context);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
