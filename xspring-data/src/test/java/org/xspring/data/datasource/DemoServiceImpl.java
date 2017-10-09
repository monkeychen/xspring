package org.xspring.data.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.xspring.data.annotation.TargetDataSource;

import java.util.List;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * <p>
 * <p>Author:ChenZhian </p>
 * <p>Create at: 2017/10/9 下午10:15</p>
 */
@Component("demoService")
public class DemoServiceImpl implements DemoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @TargetDataSource("ds_mysql")
    public void printClassroomList() {
        List list = jdbcTemplate.queryForList("SELECT * FROM classroom");
        System.out.println(list);
    }

    @Override
    @TargetDataSource("ds_postgresql")
    public void printUserList() {
        List list = jdbcTemplate.queryForList("SELECT * FROM t_user");
        System.out.println(list);
    }
}
