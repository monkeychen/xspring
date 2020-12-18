package org.xspring.tutorial.sb.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xspring.tutorial.sb.hello.entity.DemoAppAsset;

/**
 * <p>Title: DemoAppAssetRepository</p>
 * <p>Description:</p>
 * <p>@Author: chenzhian </p>
 * <p>@Date: 2020/3/20 3:31 下午</p>
 */
public interface DemoAppAssetRepository extends JpaRepository<DemoAppAsset, Long> {
    DemoAppAsset findByAppName(String appName);
}
