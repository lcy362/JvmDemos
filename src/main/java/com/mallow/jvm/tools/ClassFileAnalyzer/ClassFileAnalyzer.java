package com.mallow.jvm.tools.ClassFileAnalyzer;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by lcy on 2017/3/9.
 */
public class ClassFileAnalyzer {
    public static void main(String args[]) throws IOException {
        File file = new File("TestClass.class");
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = IOUtils.toByteArray(inputStream);
        System.out.println(binary(bytes, 16));
    }

    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }
}
