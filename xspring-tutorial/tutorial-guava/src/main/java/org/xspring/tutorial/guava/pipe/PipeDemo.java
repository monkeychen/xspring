package org.xspring.tutorial.guava.pipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * <p>Title: PipeDemo</p>
 * <p>Description: Function Description</p>
 * <p>Copyright: Ruijie Co., Ltd. (c) 2018</p>
 * <p>@Author: chenzhian</p>
 * <p>@Date: 2018/4/12 14:31</p>
 */
public class PipeDemo {

    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;

        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
        }
    }

    public static void main(String[] args) {
        boolean needDestroy = (args == null || args.length == 0 || "1".equals(args[0]));
        Process process = null;
        try {
            boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
            String dirPath = System.getProperty("user.home");
            System.out.println("user.home=" + dirPath);
            String cmd = String.format("sh -c 'ls %s'", dirPath);
            if (isWindows) {
                cmd = String.format("cmd.exe /c dir %s", dirPath);
                System.out.println("cmd = " + cmd);
                process = Runtime.getRuntime().exec(cmd);
            } else {
                System.out.println("cmd = " + cmd);
                process = Runtime.getRuntime().exec(cmd);
            }

            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            Thread.sleep(30000);
            System.out.println("Result:" + process.waitFor());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null && needDestroy) {
                System.out.println("destroy process's resource!!");
                process.destroy();
            } else {
                System.out.println("NOT destroy process's resource!!");
            }
        }
    }

}
