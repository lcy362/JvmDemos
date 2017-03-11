package com.mallow.jvm.tools.ClassFileAnalyzer;

import org.junit.Test;

/**
 * Created by l on 2017/3/11.
 */
public class ConstantPoolEnumTest {
    @Test
    public void testEnum() {
        System.out.println(ConstantPoolInfo.CONSTANT_CLASS_INFO);

        testSwitch(ConstantPoolInfo.fromTag(7));
    }

    private void testSwitch(ConstantPoolInfo i) {
        switch (i){
            case CONSTANT_CLASS_INFO:
                System.out.println("=7");
                break;
        }
    }
}
