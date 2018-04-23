package org.xspring.tutorial.guava;

import java.io.File;
import java.io.FileInputStream;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * <p>
 * <p>Author:ChenZhian </p>
 * <p>Create at: 2018/4/22 下午2:17</p>
 */
public class FileDemo {
    public static void main(String[] args) throws Exception {
        String path = "/Users/chenzhian/workspace/Java/xspring/xspring-tutorial/tutorial-guava/src/main/resources/application.properties";
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        FileInputStream fis2 = new FileInputStream(file);
        System.out.println(fis.getFD().toString());
        System.out.println(fis2.getFD().toString());
    }
}
