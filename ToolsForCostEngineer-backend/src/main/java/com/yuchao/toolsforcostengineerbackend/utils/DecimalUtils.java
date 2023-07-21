package com.yuchao.toolsforcostengineerbackend.utils;

import java.math.BigDecimal;

/**
 * @Classname DecimalUtils
 * @Description TODO
 * @Date 2023/7/18 23:32
 * @Created by YuChao
 * @Version 1.0
 */
public class DecimalUtils {
    public static BigDecimal strToDecimal(String str){
        if ("".equals(str)) return null;
        try {
            double doubleValue = Double.parseDouble(str);
            return BigDecimal.valueOf(doubleValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
