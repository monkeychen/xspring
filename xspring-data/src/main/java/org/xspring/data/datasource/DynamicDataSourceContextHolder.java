package org.xspring.data.datasource;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * <p>Title:          </p>
 * <p>Description:    </p>
 * <p>Copyright: Copyright (c) 2017  </p>
 * <p>Create Time: 2017/8/5 下午10:55 by ChenZhian            </p>
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> context = new ThreadLocal<>();

    private static Set<String> dataSourceIds = Sets.newConcurrentHashSet();

    public static void setDataSource(String dataSource) {
        context.set(dataSource);
        dataSourceIds.add(dataSource);
    }

    public static String getDataSource() {
        return context.get();
    }

    public static void clearDataSource() {
        context.remove();
    }

    public static String getDataSourceIfAbsent(String defDataSource) {
        String targetDataSource = context.get();
        if (targetDataSource == null) {
            targetDataSource = defDataSource;
            setDataSource(defDataSource);
        }
        return targetDataSource;
    }

    public static boolean isDataSourceExist(String dataSource) {
        return dataSourceIds.contains(dataSource);
    }

    public static void addDataSourceId(String dataSourceId) {
        dataSourceIds.add(dataSourceId);
    }
}
