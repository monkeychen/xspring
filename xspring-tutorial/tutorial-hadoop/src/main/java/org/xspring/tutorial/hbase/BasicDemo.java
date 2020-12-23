package org.xspring.tutorial.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

/**
 * <p>Title: BasicDemo</p>
 * <p>Description:</p>
 * <p>Copyright: FJ.CMCC Co., Ltd. (c) 2020</p>
 * <p>@Author: chenzhian </p>
 * <p>@Date: 2020/12/23 11:08 下午</p>
 */
public class BasicDemo {
    private static Configuration conf;

    private static Connection conn;

    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://localhost:9000/hbase");
        conf.set("hbase.zookeeper.quorum", "localhost");
        getConn();
    }

    public static Connection getConn() {
        if (conn == null || conn.isClosed()) {
            try {
                conn = ConnectionFactory.createConnection(conf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static void closeConn() {
        if (conn != null) {
            try {
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createTable(String tbName, String... familyColumns) {
        TableName tn = TableName.valueOf(tbName);
        try {
            Admin admin = getConn().getAdmin();
            if (admin.tableExists(tn)) {
                return;
            }
            TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tn);
//            HTableDescriptor htd = new HTableDescriptor(tn);
            for (String fc : familyColumns) {
                ColumnFamilyDescriptor cfd = ColumnFamilyDescriptorBuilder.of(fc);
                tableDescriptorBuilder.setColumnFamily(cfd);
//                HColumnDescriptor hcd = new HColumnDescriptor(fc);
//                htd.addFamily(hcd);
            }
            admin.createTable(tableDescriptorBuilder.build());
            admin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
