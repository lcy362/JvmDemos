package com.mallow.jvm.tools.ClassFileAnalyzer;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by lcy on 2017/3/9.
 */
public class ClassFileAnalyzer {
    public static void main(String args[]) throws IOException {
        File file = new File("TestClass.class");
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = IOUtils.toByteArray(inputStream);
        int curse = 0;
        byte[] magic = Arrays.copyOfRange(bytes, curse, curse+=4);
        System.out.println("magic is: " + binary(magic, 16));
    }

    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }
}
