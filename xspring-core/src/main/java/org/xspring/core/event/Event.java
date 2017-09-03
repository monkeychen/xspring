package org.xspring.core.event;

/**
 * <p>Title:          </p>
 * <p>Description:    </p>
 * <p>Copyright: Copyright (c) 2017  </p>
 * <p>Create Time: 2017/8/20 上午9:15 by ChenZhian            </p>
 */
public interface Event {
    /**
     * 是否为异步事件
     * @return boolean
     */
    default boolean isAsync() {
        return false;
    }

    /**
     * 该事件将发往的目标总线名,如果为null或空串,则统一发往默认的总线,同步或异步由isAsync()方法决定
     * @return 目标总线名
     */
    default String getTargetEventBusName() {
        return null;
    }

}
