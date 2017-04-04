package com.mallow.jvm.tools.ClassFileAnalyzer;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2017/3/31.
 */
@Data
public class ConstantPool {
    private ConstantPoolInfo type;
    private String info;
    private Map<String, Integer> refs;
}
