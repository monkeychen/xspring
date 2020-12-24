package org.xspring.tutorial.hbase;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
        closeResource(conn);
    }

    public static void createTable(String tbName, String... familyColumns) {
        TableName tn = TableName.valueOf(tbName);
        Admin admin = null;
        try {
            admin = getConn().getAdmin();
            if (admin.tableExists(tn)) {
                return;
            }
            TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tn);
            for (String fc : familyColumns) {
                ColumnFamilyDescriptor cfd = ColumnFamilyDescriptorBuilder.of(fc);
                tableDescriptorBuilder.setColumnFamily(cfd);
            }
            admin.createTable(tableDescriptorBuilder.build());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource(admin);
        }
    }

    public static void dropTable(String tbName) {
        TableName tn = TableName.valueOf(tbName);
        Admin admin = null;
        try {
            admin = getConn().getAdmin();
            admin.disableTable(tn);
            admin.deleteTable(tn);
            admin.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource(admin);
        }
    }

    public static boolean saveOrUpdate(String tbName, String rowKey, String family, String qualifier, String value) {
        Table table = null;
        try {
            table = getConn().getTable(TableName.valueOf(tbName));
            Put putData = new Put(Bytes.toBytes(rowKey));
            putData.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
            table.put(putData);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResource(table);
        }
        return true;
    }

    public static boolean delete(String tbName, String rowKey, String family, String qualifier) {
        Table table = null;
        try {
            table = getConn().getTable(TableName.valueOf(tbName));
            Delete deleteData = new Delete(Bytes.toBytes(rowKey));
            if (qualifier != null) {
                deleteData.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
            } else if (family != null) {
                deleteData.addFamily(Bytes.toBytes(family));
            }
            table.delete(deleteData);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResource(table);
        }
        return true;
    }

    public static boolean deleteFamily(String tbName, String rowKey, String family) {
        return delete(tbName, rowKey, family, null);
    }

    public static boolean deleteRow(String tbName, String rowKey) {
        return delete(tbName, rowKey, null, null);
    }

    public static String getCellValue(String tbName, String rowKey, String family, String qualifier) {
        Table table = null;
        try {
            table = getConn().getTable(TableName.valueOf(tbName));
            Get getData = new Get(Bytes.toBytes(rowKey));
            getData.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
            Result result = table.get(getData);
            return result.listCells().size() > 0 ? Bytes.toString(CellUtil.cloneValue(result.listCells().get(0))) : null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource(table);
        }
        return null;
    }

    public static Map<String, String> getFamily(String tbName, String rowKey, String family) {
        Map<String, String> resultMap = Maps.newHashMap();
        Table table = null;
        try {
            table = getConn().getTable(TableName.valueOf(tbName));
            Get getData = new Get(Bytes.toBytes(rowKey));
            getData.addFamily(Bytes.toBytes(family));
            Result result = table.get(getData);
            List<Cell> cellList = result.listCells();
            for (Cell cell : cellList) {
                resultMap.put(Bytes.toString(CellUtil.cloneQualifier(cell)), Bytes.toString(CellUtil.cloneValue(cell)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource(table);
        }
        return resultMap;
    }

    public static Map<String, Map<String, String>> getRow(String tbName, String rowKey) {
        Table table = null;
        try {
            table = getConn().getTable(TableName.valueOf(tbName));
            Get getData = new Get(Bytes.toBytes(rowKey));
            Result result = table.get(getData);
            return parseRowResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource(table);
        }
        return Maps.newHashMap();
    }

    private static Map<String, Map<String, String>> parseRowResult(Result result) {
        Map<String, Map<String, String>> resultMap = Maps.newHashMap();
        String rowKey = Bytes.toString(result.getRow());
        List<Cell> cellList = result.listCells();
        for (Cell cell : cellList) {
            String familyName = Bytes.toString(CellUtil.cloneFamily(cell));
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            Map<String, String> familyMap = resultMap.computeIfAbsent(familyName, k -> Maps.newHashMap());
            familyMap.put(qualifier, value);
        }
        return resultMap;
    }

    public static List<Map<String, Map<String, String>>> getRowList(String tbName) {
        return getRowList(tbName, null, null);
    }

    public static List<Map<String, Map<String, String>>> getRowList(String tbName, String start, String stop) {
        List<Map<String, Map<String, String>>> rowList = Lists.newArrayList();
        Admin admin = null;
        TableName tn = TableName.valueOf(tbName);
        try {
            admin = getConn().getAdmin();
            if (!admin.tableExists(tn)) {
                return rowList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource(admin);
        }
        Table table = null;
        try {
            table = getConn().getTable(tn);
            Scan scan = new Scan();
            if (!Strings.isNullOrEmpty(start) && !Strings.isNullOrEmpty(stop)) {
                scan.withStartRow(Bytes.toBytes(start));
                scan.withStopRow(Bytes.toBytes(stop), false);
                scan.setBatch(1000);
            }
            ResultScanner scanner = table.getScanner(scan);
            for (Result result : scanner) {
                rowList.add(parseRowResult(result));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource(table);
        }
        return rowList;
    }

    public static void closeResource(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        System.out.println(getRowList("scores"));
//        createTable("api_table", "apics");
//        saveOrUpdate("api_table", "NewUser", "apics", "new_column_1", "simiam1");
//        saveOrUpdate("api_table", "NewUser", "apics", "new_column_2", "simiam2");
//        System.out.println(getCellValue("api_table", "NewUser", "apics", "new_column_1"));
//        System.out.println(getFamily("api_table", "NewUser", "apics"));
//        System.out.println(getRow("api_table", "NewUser"));
//        System.out.println(getRowList("api_table"));
//        deleteRow("api_table", "NewUser");
//        System.out.println(getRowList("api_table"));
//        dropTable("api_table");
        System.out.println("===========>>>>" + getCellValue("namelist", "song1", "details", "rank"));
    }
}
