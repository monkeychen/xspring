package org.xspring.core.eventbus;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.xspring.core.Constants;
import org.xspring.core.event.Event;
import org.xspring.core.annotation.EventBusDescription;
import org.xspring.core.annotation.EventSubscriber;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * <p>
 * <p>Author:ChenZhian </p>
 * <p>Create at: 2017/8/17 20:52</p>
 */
public class EventBusFactory {
    private static final Logger logger = LoggerFactory.getLogger(EventBusFactory.class);

    private static EventBusFactory INSTANCE;

    private String syncEventBusSuffix = "_sync_event_bus";

    private String asyncEventBusSuffix = "_async_event_bus";

    private Map<String, EventBus> eventBusCache = Maps.newConcurrentMap();

    private ExecutorService executor;

    private EventBus syncEventBus;

    private EventBus asyncEventBus;

    public static EventBusFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (EventBusFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EventBusFactory();
                }
            }
        }
        return INSTANCE;
    }

    private EventBusFactory() {
        init();
    }

    private void init() {
        String defSyncBusName = Constants.DEFAULT_BUS_NAME + syncEventBusSuffix;
        String defAsyncBusName = Constants.DEFAULT_BUS_NAME + asyncEventBusSuffix;
        syncEventBus = new EventBus(defSyncBusName);
        ThreadFactoryBuilder builder = new ThreadFactoryBuilder().setNameFormat("DefaultEventBusThreadPool-%d");
        ThreadFactory factory = builder.build();
        executor = Executors.newFixedThreadPool(10, factory);
        asyncEventBus = new AsyncEventBus(defAsyncBusName, executor);
        eventBusCache.put(defSyncBusName, syncEventBus);
        eventBusCache.put(defAsyncBusName, asyncEventBus);
    }

    public EventBus getEventBus(String id) {
        return eventBusCache.get(id);
    }

    public void post(Event event) {
        String targetBusName = event.getTargetEventBusName();
        if (Strings.isNullOrEmpty(targetBusName)) {
            targetBusName = Constants.DEFAULT_BUS_NAME;
        }
        boolean async = event.isAsync();
        targetBusName = targetBusName + (async ? asyncEventBusSuffix : syncEventBusSuffix);
        EventBus targetBus = eventBusCache.getOrDefault(targetBusName, async ? asyncEventBus: syncEventBus);
        targetBus.post(event);
    }


    public void registerEventSubscriber(ApplicationContext context) {
        Map<String, Object> beanMap = context.getBeansWithAnnotation(EventSubscriber.class);
        if (beanMap == null || beanMap.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            String beanId = entry.getKey();
            Object bean = entry.getValue();
            EventSubscriber subscriber = bean.getClass().getAnnotation(EventSubscriber.class);
            if (subscriber == null) {
                continue;
            }
            boolean isAsync = subscriber.async();
            EventBusDescription description = subscriber.description();
            String busNamePrefix = description.name();
            int threadNum = description.threadNum();
            String busName = busNamePrefix + (isAsync ? asyncEventBusSuffix : syncEventBusSuffix);
            String beanClassName = bean.getClass().getSimpleName();
            logger.info("Found bean[id={}, class={}] with @EventSubscriber[async={}, descr=[busName={}, threadNum={}]].",
                    beanId, beanClassName, isAsync, busNamePrefix, threadNum);
            EventBus targetEventBus = eventBusCache.computeIfAbsent(busName, tmpBusName -> {
                if (isAsync) {
                    String threadPoolName = busNamePrefix + "ThreadPool-%d";
                    ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
                    ThreadFactory factory = builder.setNameFormat(threadPoolName).build();
                    return new AsyncEventBus(tmpBusName, Executors.newFixedThreadPool(threadNum, factory));
                } else {
                    return new EventBus(tmpBusName);
                }
            });
            targetEventBus.register(bean);
            logger.info("Success to register bean[id={}, class={}] to EventBus[{}].", beanId, beanClassName, busName);
        }
    }
}
