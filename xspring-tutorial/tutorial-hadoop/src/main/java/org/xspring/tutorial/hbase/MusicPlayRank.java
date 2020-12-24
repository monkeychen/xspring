package org.xspring.tutorial.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;
import java.util.List;

/**
 * <p>Title: MusicPlayRank</p>
 * <p>Description:</p>
 * <p>Copyright: FJ.CMCC Co., Ltd. (c) 2020</p>
 * <p>@Author: chenzhian </p>
 * <p>@Date: 2020/12/25 12:20 上午</p>
 */
public class MusicPlayRank {
    public static class MyMapper extends TableMapper<Text, IntWritable> {
        @Override
        protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
            List<Cell> cellList = value.listCells();
            for (Cell cell : cellList) {
                context.write(new Text(Bytes.toString(CellUtil.cloneValue(cell))), new IntWritable(1));
            }
        }
    }

    public static class MyReducer extends TableReducer<Text, IntWritable, Text> {
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int playCnt = 0;
            for (IntWritable num : values) {
                playCnt += num.get();
            }
            Put putData = new Put(Bytes.toBytes(key.toString()));
            putData.addColumn(Bytes.toBytes("details"), Bytes.toBytes("rank"), Bytes.toBytes("" + playCnt));
            context.write(key, putData);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://localhost:9000/hbase");
        conf.set("hbase.zookeeper.quorum", "localhost");

        Job job = Job.getInstance(conf, "music-rank");
        job.setJarByClass(MusicPlayRank.class);
        job.setNumReduceTasks(1);
        Scan scan = new Scan();
        scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"));
        TableMapReduceUtil.initTableMapperJob("music", scan, MyMapper.class, Text.class, IntWritable.class, job);
        TableMapReduceUtil.initTableReducerJob("namelist", MyReducer.class, job);
        job.waitForCompletion(true);
        System.out.println("执行成功，统计结果存于namelist表中。");
    }
}
