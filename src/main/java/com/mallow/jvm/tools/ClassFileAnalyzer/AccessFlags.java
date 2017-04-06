package com.mallow.jvm.tools.ClassFileAnalyzer;

import java.lang.reflect.Field;

/**
 * Created by lcy on 2017/4/5.
 */
public class AccessFlags {
    public static final short ACC_PUBLIC = 0x0001;
    public static final short ACC_PRIVATE = 0x0002;
    public static final short ACC_PROTECTED = 0x0004;
    public static final short ACC_STATIC = 0x0008;
    public static final short ACC_FINAL = 0x0010;
    public static final short ACC_SUPER = 0x0020;
    public static final short ACC_VOLATILE = 0x0040;
    public static final short ACC_TRANSIENT = 0x0080;
    public static final short ACC_NATIVE = 0x0100;
    public static final short ACC_INTERFACE = 0x0200;
    public static final short ACC_ABSTRACT = 0x0400;
    public static final short ACC_STRICTFP = 0x0800;
    public static final short ACC_SYNTHETIC = 0x1000;
    public static final short ACC_ANNOTATION = 0x2000;
    public static final short ACC_ENUM = 0x4000;

    public static String getAccessFlags(String accessFlags) {
        short shortFlag = Short.parseShort(accessFlags, 16);

        StringBuilder flag = new StringBuilder();

        Field[] fields = AccessFlags.class.getFields();
        for (Field field : fields) {
            String name = field.getName();
            if (!name.startsWith("ACC_")) {
                continue;
            }
            short value = 0;
            try {
                value = field.getShort(AccessFlags.class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if ((value&shortFlag) != 0) {
                flag.append(name.split("_")[1] + " ");
            }
        }
        System.out.println(flag.toString());
        return null;

    }
}
