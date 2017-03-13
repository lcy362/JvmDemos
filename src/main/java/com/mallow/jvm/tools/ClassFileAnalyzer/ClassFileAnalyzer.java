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
        byte[] minorVersion = Arrays.copyOfRange(bytes, curse, curse+=2);
        byte[] majorVersion = Arrays.copyOfRange(bytes, curse, curse+=2);
        System.out.println("majorVersion: " + binary(majorVersion, 10));
        System.out.println("minorVersion: " + binary(minorVersion, 10));
        byte[] constantPoolSize = Arrays.copyOfRange(bytes, curse, curse+=2);
        int constantSize = Integer.parseInt(binary(constantPoolSize, 10));
        System.out.println("constantPoolSize: " + constantSize);
        for (int i = 0; i < constantSize; i++) {
            byte[] constanTag = Arrays.copyOfRange(bytes, curse, curse+=1);
            int tag = Integer.parseInt(binary(constanTag, 10));
            ConstantPoolInfo poolInfo = ConstantPoolInfo.fromTag(tag);
            System.out.println(poolInfo);
            switch (poolInfo) {
                case CONSTANT_METHODREF_INFO:
                    byte[] classIndex = Arrays.copyOfRange(bytes, curse, curse+=2);
                    byte[] nameAndTypeIndex = Arrays.copyOfRange(bytes, curse, curse+=2);
                    System.out.println("class index: " + binary(classIndex, 10));
                    System.out.println("name and type index: " + binary(nameAndTypeIndex, 10));
                    break;
            }
        }
    }

    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }
}
