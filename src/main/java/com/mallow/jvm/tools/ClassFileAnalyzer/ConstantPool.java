package com.mallow.jvm.tools.ClassFileAnalyzer;

import lombok.Data;

import java.util.List;

/**
 * Created by lcy on 2017/3/31.
 */
@Data
public class ConstantPool {
    private ConstantPoolInfo type;
    private String info;
    private List<Integer> refs;
}
