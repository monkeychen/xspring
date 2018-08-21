package org.xspring.tutorial.guava;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * <p>
 * <p>Author:ChenZhian </p>
 * <p>Create at: 2018/4/22 下午2:17</p>
 */
public class FileDemo {

    private String f1 = getF1();

    {
        System.out.println("code block....");
    }

    static {
        System.out.println("static code block");
    }

    public FileDemo() {
        System.out.println("FileDemo() constructor ...");
        System.out.println("1 -> a:" + f1);
        f1 = "bbbb";
        System.out.println("2 -> a:" + f1);
    }

    public static String getF1() {
        System.out.println("invoke getF1() ...");
        return "aaaa";
    }

    public static void main(String[] args) throws Exception {
//        String path = "/Users/chenzhian/workspace/Java/xspring/xspring-tutorial/tutorial-guava/src/main/resources/application.properties";
//        File file = new File(path);
//        FileInputStream fis = new FileInputStream(file);
//        FileInputStream fis2 = new FileInputStream(file);
//        System.out.println(fis.getFD().toString());
//        System.out.println(fis2.getFD().toString());

        String hello = "hello";
        String world = "world";
        String str = hello + world;
        System.out.println(str);

        byte a = 127;
        byte b = 127;
        //b = a + b;
        b += a;

        int c = 11111;
        int d = 11111;
        System.out.println((c == d));

        System.out.println(System.getProperty("sun.arch.data.model"));

        Runtime runtime = Runtime.getRuntime();
        System.out.println("free-memory:" + runtime.freeMemory());
        System.out.println("total-memory:" + runtime.totalMemory());
        System.out.println("max-memory:" + runtime.maxMemory());

        float f = 3.14F;

        int[] arr = {1, 2, 3};

        System.out.println("=========");
        FileDemo demo = new FileDemo();
        demo.changeArrValue(arr);
        System.out.println("after invoke:" + Arrays.toString(arr));

        String str1 = "hello1";
        String str2 = "hello1";
        System.out.println(str1 == str2);
        String str3 = new String("hello1");
        String str4 = new String("hello1");
        System.out.println(str3 == str4);

    }

    private void changeArrValue(int[] arr) {
        System.out.println("before change:" + Arrays.toString(arr));
//        arr = new int[]{7, 9, 8};
//        System.out.println(Arrays.toString(arr));
        arr[0] = 5;
        System.out.println(Arrays.toString(arr));
        //new FileDemo();

        class c1 {
            private int a;
        }
    }
}
