package org.xspring.tutorial.hadoop;

import com.google.common.base.Strings;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * <p>Title: SortDemo</p>
 * <p>Description:</p>
 * <p>Copyright: FJ.CMCC Co., Ltd. (c) 2020</p>
 * <p>@Author: chenzhian </p>
 * <p>@Date: 2020/12/18 5:10 下午</p>
 */
public class SortDemo {
    public static class SortMapper extends Mapper<Object, Text, IntWritable, IntWritable> {
        private static IntWritable data = new IntWritable();

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            if (Strings.isNullOrEmpty(line)) {
                return;
            }
            data.set(Integer.parseInt(line.trim()));
            context.write(data, new IntWritable(1));
        }
    }

    public static class SortReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
        private static IntWritable lineNum = new IntWritable(1);

        @Override
        protected void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            for (IntWritable value : values) {
                context.write(lineNum, key);
                lineNum = new IntWritable(lineNum.get() + 1);
            }
        }
    }

    public static void testHdfsApi(String input) throws IOException, URISyntaxException {
        Configuration conf = new Configuration();
        URI uri = new URI("hdfs://localhost:9000/");
        FileSystem fs = FileSystem.get(uri, conf);
        if (fs.exists(new Path(input))) {
            System.out.println("File exist!");
        } else {
            System.out.println("File NOT exist!");
        }
    }

    public static JobConf getLocalJobConf() {
        JobConf conf = new JobConf();
        String projectDir = "/Users/chenzhian/workspace/Java/xspring/xspring-tutorial/tutorial-hadoop";
        String input = projectDir + "/input/sort01.log";
        String output = projectDir + "/output/demo_sort_output";
        conf.set("mapreduce.input.fileinputformat.inputdir", input);
        conf.set("mapreduce.output.fileoutputformat.outputdir", output);
        return conf;
    }

    public static JobConf getRemoteJobConf() {
        JobConf conf = new JobConf();
        String input = "hdfs://localhost:9000//user/chenzhian/input/sort01.log";
        String output = "hdfs://localhost:9000//user/chenzhian/demo_sort_output";
        conf.set("mapreduce.input.fileinputformat.inputdir", input);
        conf.set("mapreduce.output.fileoutputformat.outputdir", output);
        return conf;
    }

    public static void runHadoopJob(JobConf jobConf) throws InterruptedException, IOException, ClassNotFoundException {
        Job job = Job.getInstance(jobConf, "Data Sort");
        job.setJarByClass(SortDemo.class);
        job.setMapperClass(SortMapper.class);
        job.setReducerClass(SortReducer.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static void main(String[] args) throws Exception {
//        runHadoopJob(getLocalJobConf());
//        runHadoopJob(getRemoteJobConf());
        String input = "hdfs://localhost:9000//user/chenzhian/input/sort01.log";
        testHdfsApi(input);

    }
}
