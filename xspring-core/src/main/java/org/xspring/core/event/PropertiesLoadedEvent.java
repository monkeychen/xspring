package org.xspring.core.event;

/**
 * <p>Title:          </p>
 * <p>Description:    </p>
 * <p>Copyright: Copyright (c) 2017  </p>
 * <p>Create Time: 2017/8/20 上午10:37 by ChenZhian            </p>
 */
public class PropertiesLoadedEvent implements Event {
    @Override
    public boolean isAsync() {
        return true;
    }
}
