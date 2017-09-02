package org.xspring.data.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.xspring.data.annotation.TargetDataSource;

/**
 * <p>Title:          </p>
 * <p>Description:    </p>
 * <p>Copyright: Copyright (c) 2017  </p>
 * <p>Create Time: 2017/8/5 下午11:56 by ChenZhian            </p>
 */
@Aspect
@Order(-10)
@Component
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Before("@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource) throws Throwable {
        String dataSourceId = targetDataSource.value();
        if (DynamicDataSourceContextHolder.isDataSourceExist(dataSourceId)) {
            DynamicDataSourceContextHolder.setDataSource(dataSourceId);
        } else {
            Signature signature = joinPoint.getSignature();
            logger.warn("Fail to find target data source[{}], so using the default data source.The join point:[{}]!",
                    dataSourceId, signature);
        }
    }

    @After("@annotation(targetDataSource)")
    public void resetDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        logger.warn("Reset data source to default.The join point:[{}]!", joinPoint.getSignature());
        DynamicDataSourceContextHolder.clearDataSource();
    }
}
