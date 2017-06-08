package com.mallow.jvm.tools.classanalyzer;

public enum ConstantPoolInfo {
    CONSTANT_UTF8_INFO(1, " UTF-8"), CONSTANT_INTEGER_INFO(3, "Integer"),CONSTANT_FLOAT_INFO(4, "Float"),
    CONSTANT_LONG_INFO(5, "Long"), CONSTANT_DOUBLE_INFO(6, "Double"),CONSTANT_CLASS_INFO(7, "Class"),
    CONSTANT_STRING_INFO(8, "String"),CONSTANT_FIELDREF_INFO(9, "Fieldref"), CONSTANT_METHODREF_INFO(10, "Methodref"),
    CONSTANT_INTERFACEMETHODREF_INFO(11, "InterfaceMethodref"), CONSTANT_NAMEANDTYPE_INFO(12, "NameAndType"),
    CONSTANT_METHODHANDLE_INFO(15, "MethodHandle"), CONSTANT_METHODTYPE_INFO(16, "MethodType"),
    CONSTANT_INVOKEDYNAMIC_INFO(18, "InvokeDynamic");

    private int tag;
    private String description;

    ConstantPoolInfo(int tag, String description) {
        this.tag = tag;
        this.description = description;
    }

    public int getTag() {
        return tag;
    }

    public static ConstantPoolInfo fromTag(int tag) {
        for (ConstantPoolInfo info : ConstantPoolInfo.values()) {
            if (tag == info.getTag()) {
                return info;
            }
        }
        throw new IllegalArgumentException("tag not exist: " + tag);
    }
}
