package com.mallow.jvm.tools.ClassFileAnalyzer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by lcy on 2017/3/9.
 */
@Slf4j
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
        List<ConstantPool> pool = new ArrayList<>();
        pool.add(new ConstantPool()); //first constant
        for (int i = 1; i < constantSize; i++) {
            ConstantPool constant = new ConstantPool();
            byte[] constanTag = Arrays.copyOfRange(bytes, curse, curse+=1);
            int tag = Integer.parseInt(binary(constanTag, 10));
            ConstantPoolInfo poolInfo = ConstantPoolInfo.fromTag(tag);
            constant.setType(poolInfo);
            Map<String, Integer> refs = new HashMap<>();
            constant.setRefs(refs);
            switch (poolInfo) {
                case CONSTANT_METHODREF_INFO: case CONSTANT_FIELDREF_INFO:
                    byte[] classIndex = Arrays.copyOfRange(bytes, curse, curse+=2);
                    byte[] nameAndTypeIndex = Arrays.copyOfRange(bytes, curse, curse+=2);
                    refs.put("class index", binaryToDecimal(classIndex));
                    refs.put("name and type index", binaryToDecimal(nameAndTypeIndex));
                    break;
                case CONSTANT_CLASS_INFO: case CONSTANT_STRING_INFO:
                    byte[] index = Arrays.copyOfRange(bytes, curse, curse+=2);
                    refs.put("index", binaryToDecimal(index));
                    break;
                case CONSTANT_INTEGER_INFO: case CONSTANT_LONG_INFO: case CONSTANT_DOUBLE_INFO:
                    byte[] value4 = Arrays.copyOfRange(bytes, curse, curse+=4);
                    constant.setInfo(binary(value4, 10));
//                    System.out.println("value: " + binary(value4, 10));
                    break;
                case CONSTANT_FLOAT_INFO:
                    byte[] value2 = Arrays.copyOfRange(bytes, curse, curse+=2);
                    constant.setInfo(binary(value2, 10));
//                    System.out.println("value: " + binary(value2, 10));
                    break;
                case CONSTANT_UTF8_INFO:
                    byte[] length = Arrays.copyOfRange(bytes, curse, curse+=2);
                    int intLength = Integer.parseInt(binary(length, 10));
//                    System.out.println("utf8 length: " + binary(length, 10));
                    byte[] utf8Value = Arrays.copyOfRange(bytes, curse, curse+=intLength);
//                    System.out.println("utf8 value: " + new String(utf8Value, "UTF-8"));
                    constant.setInfo(new String(utf8Value, "UTF-8"));
                    break;
                case CONSTANT_NAMEANDTYPE_INFO:
                    byte[] nameIndex = Arrays.copyOfRange(bytes, curse, curse+=2);
                    byte[] descriptorIndex = Arrays.copyOfRange(bytes, curse, curse+=2);
                    refs.put("nameIndex", binaryToDecimal(nameIndex));
                    refs.put("descriptorIndex", binaryToDecimal(descriptorIndex));
                    break;
                case CONSTANT_METHODHANDLE_INFO:
                    byte[] referenceIndex1 = Arrays.copyOfRange(bytes, curse, curse+=1);
                    byte[] referenceIndex2 = Arrays.copyOfRange(bytes, curse, curse+=2);
                    refs.put("referenceIndex1", binaryToDecimal(referenceIndex1));
                    refs.put("referenceIndex2", binaryToDecimal(referenceIndex2));
                    break;
                case CONSTANT_METHODTYPE_INFO:
                    byte[] descriptorIndex2 = Arrays.copyOfRange(bytes, curse, curse+=2);
                    refs.put("descriptorIndex", binaryToDecimal(descriptorIndex2));
                    break;
                case CONSTANT_INVOKEDYNAMIC_INFO:
                    byte[] bootstrapMethodAttrIndex = Arrays.copyOfRange(bytes, curse, curse+=2);
                    byte[] nameAndTypeIndex2 = Arrays.copyOfRange(bytes, curse, curse+=2);
                    refs.put("bootstrapMethodAttrIndex", binaryToDecimal(bootstrapMethodAttrIndex));
                    refs.put("nameAndTypeIndex", binaryToDecimal(nameAndTypeIndex2));
                    break;
                case CONSTANT_INTERFACEMETHODREF_INFO:
                    byte[] classIndex2 = Arrays.copyOfRange(bytes, curse, curse+=2);
                    byte[] nameAndTypeIndexU2 = Arrays.copyOfRange(bytes, curse, curse+=2);
                    refs.put("nameIndex", binaryToDecimal(classIndex2));
                    refs.put("descriptorIndex", binaryToDecimal(nameAndTypeIndexU2));
                    break;
            }
            pool.add(constant);
        }
        printConstantPool(pool, constantSize);
    }

    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

    public static int binaryToDecimal(byte[] bytes){
        return new BigInteger(1, bytes).intValue();
    }

    private static void printConstantPool(List<ConstantPool> pool, int constantSize) {
        for (int i = 1; i < constantSize; i++) {
            StringBuilder builder = new StringBuilder();
            builder.append(i + "th constant: ");
            ConstantPool constant = pool.get(i);
            builder.append(constant.getType());
            if (isInfoConstant(constant)) {
                builder.append("--" + printInfoPool(constant));
            } else {
                builder.append(printRefPool(constant, pool));
            }
            log.info(builder.toString());
        }
    }

    private static String printInfoPool(ConstantPool constant) {
        if (!isInfoConstant(constant)) {
            log.error("not a info constant: " + constant);
            return null;
        }
        return constant.getInfo();
    }

    private static String printRefPool(ConstantPool constant, List<ConstantPool> pool) {
        if (isInfoConstant(constant)) {
            log.error("not a ref constant: " + constant);
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Integer> ref : constant.getRefs().entrySet()) {
            builder.append("\n");
            builder.append(ref.getKey() + "=" + ref.getValue() + "");
            ConstantPool refConstant = pool.get(ref.getValue());
            if (isInfoConstant(refConstant)) {
                builder.append("(" + printInfoPool(refConstant) + ")");
            } else {
                builder.append(":" + refConstant.getType() + "---");
                builder.append(printRefPool(refConstant, pool));
            }
        }
        return builder.toString();
    }

    private static boolean isInfoConstant(ConstantPool constant) {
        return StringUtils.isNotBlank(constant.getInfo()) && constant.getRefs().size() == 0;
    }
}
