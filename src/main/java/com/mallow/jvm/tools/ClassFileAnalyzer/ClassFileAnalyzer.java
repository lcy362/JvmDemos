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
        for (int i = 1; i < constantSize; i++) {
            byte[] constanTag = Arrays.copyOfRange(bytes, curse, curse+=1);
            int tag = Integer.parseInt(binary(constanTag, 10));
            ConstantPoolInfo poolInfo = ConstantPoolInfo.fromTag(tag);
            System.out.println(i + "th: " + poolInfo);
            switch (poolInfo) {
                case CONSTANT_METHODREF_INFO: case CONSTANT_FIELDREF_INFO:
                    byte[] classIndex = Arrays.copyOfRange(bytes, curse, curse+=2);
                    byte[] nameAndTypeIndex = Arrays.copyOfRange(bytes, curse, curse+=2);
                    System.out.println("class index: " + binary(classIndex, 10));
                    System.out.println("name and type index: " + binary(nameAndTypeIndex, 10));
                    break;
                case CONSTANT_CLASS_INFO: case CONSTANT_STRING_INFO:
                    byte[] index = Arrays.copyOfRange(bytes, curse, curse+=2);
                    System.out.println("index: " + binary(index, 10));
                    break;
                case CONSTANT_INTEGER_INFO: case CONSTANT_LONG_INFO: case CONSTANT_DOUBLE_INFO:
                    byte[] value4 = Arrays.copyOfRange(bytes, curse, curse+=4);
                    System.out.println("value: " + binary(value4, 10));
                    break;
                case CONSTANT_FLOAT_INFO:
                    byte[] value2 = Arrays.copyOfRange(bytes, curse, curse+=2);
                    System.out.println("value: " + binary(value2, 10));
                    break;
                case CONSTANT_UTF8_INFO:
                    byte[] length = Arrays.copyOfRange(bytes, curse, curse+=2);
                    int intLength = Integer.parseInt(binary(length, 10));
                    System.out.println("utf8 length: " + binary(length, 10));
                    byte[] utf8Value = Arrays.copyOfRange(bytes, curse, curse+=intLength);
                    System.out.println("utf8 value: " + new String(utf8Value, "UTF-8"));
                    break;
                case CONSTANT_NAMEANDTYPE_INFO:
                    byte[] nameIndex = Arrays.copyOfRange(bytes, curse, curse+=2);
                    byte[] descriptorIndex = Arrays.copyOfRange(bytes, curse, curse+=2);
                    System.out.println("nameIndex: " + binary(nameIndex, 10));
                    System.out.println("descriptorIndex: " + binary(descriptorIndex, 10));
                    break;
                case CONSTANT_METHODHANDLE_INFO:
                    byte[] referenceIndex1 = Arrays.copyOfRange(bytes, curse, curse+=1);
                    byte[] referenceIndex2 = Arrays.copyOfRange(bytes, curse, curse+=2);
                    System.out.println("referenceIndex1: " + binary(referenceIndex1, 10));
                    System.out.println("referenceIndex2: " + binary(referenceIndex2, 10));
                    break;
                case CONSTANT_METHODTYPE_INFO:
                    byte[] descriptorIndex2 = Arrays.copyOfRange(bytes, curse, curse+=2);
                    System.out.println("descriptorIndex: " + binary(descriptorIndex2, 10));
                    break;
                case CONSTANT_INVOKEDYNAMIC_INFO:
                    byte[] bootstrapMethodAttrIndex = Arrays.copyOfRange(bytes, curse, curse+=2);
                    byte[] nameAndTypeIndex2 = Arrays.copyOfRange(bytes, curse, curse+=2);
                    System.out.println("bootstrapMethodAttrIndex: " + binary(bootstrapMethodAttrIndex, 10));
                    System.out.println("nameAndTypeIndex: " + binary(nameAndTypeIndex2, 10));
                    break;
                case CONSTANT_INTERFACEMETHODREF_INFO:
                    byte[] classIndex2 = Arrays.copyOfRange(bytes, curse, curse+=2);
                    byte[] nameAndTypeIndexU2 = Arrays.copyOfRange(bytes, curse, curse+=2);
                    System.out.println("nameIndex: " + binary(classIndex2, 10));
                    System.out.println("descriptorIndex: " + binary(nameAndTypeIndexU2, 10));
                    break;
            }
        }
    }

    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }
}
