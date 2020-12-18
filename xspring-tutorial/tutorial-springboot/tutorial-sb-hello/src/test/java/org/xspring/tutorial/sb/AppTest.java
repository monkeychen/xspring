package org.xspring.tutorial.sb;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.xspring.tutorial.sb.hello.entity.DemoAppAsset;
import org.xspring.tutorial.sb.hello.repository.DemoAppAssetRepository;

/**
 * <p>Title: AppTest</p>
 * <p>Description:</p>
 * <p>Copyright: FJ.CMCC Co., Ltd. (c) 2020</p>
 * <p>@Author: chenzhian </p>
 * <p>@Date: 2020/3/20 6:13 下午</p>
 */
@SpringBootApplication
@SpringBootTest
@RunWith(SpringRunner.class)
@ServletComponentScan
public class AppTest {

    @Autowired
    private DemoAppAssetRepository demoAppAssetRepository;

    @Test
    public void testSaveAppAsset() {
        DemoAppAsset appAsset = new DemoAppAsset();
        appAsset.setAppName("dss");
        appAsset.setDepartment("JZXN");
        appAsset.setAppIntro("dss test");
        demoAppAssetRepository.save(appAsset);
        DemoAppAsset dbAppAsset = demoAppAssetRepository.findByAppName("dss");
        Assert.assertNotNull(dbAppAsset);
    }
}
