package org.xspring.core.eventbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * <p>
 * <p>Author:ChenZhian </p>
 * <p>Create at: 2017/8/18 21:47</p>
 */
@Component
public class EventBusService implements SmartLifecycle, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventBusService.class);

    private ApplicationContext context;

    private boolean isRunning = false;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        this.stop();
        callback.run();
    }

    @Override
    public synchronized void start() {
        if (isRunning) {
            logger.warn("EventBusService has been startup, so it's not necessary to startup again.");
            return;
        }
        // 加载所有使用EventSubscriber注解的订阅者
        isRunning = true;
        EventBusFactory.getInstance().registerEventSubscriber(context);
        logger.info("Success to startup the EventBusService!");
    }

    @Override
    public void stop() {
        // 卸载所有使用EventSubscriber注解的订阅者
        EventBusFactory.getInstance().unRegisterEventSubscriber(context);
        isRunning = false;
        logger.info("Success to shutdown the EventBusService!");
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return 5;
    }
}
