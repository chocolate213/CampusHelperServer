package cn.jxzhang.common.utils;

import java.util.Random;
import java.util.UUID;

/**
 * Created on 2016-11-06 16:12
 * <p>Title:       CommonUtils</p>
 * <p>Description: Common Utils</p>
 * <p>Company:     [Company Name]</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 *
 * @author <a href=zhangjx_dev@163.com>J.X.Zhang</a>
 * @version 1.0
 */
public class CommonUtils {

    private CommonUtils() { /* cannot be instantiated */ }

    /**
     * Retrieve a type 4 (pseudo randomly generated) UUID.
     *
     * @return A randomly generated {@code UUID}
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**
     * generate random number by given length.
     *
     * @param length random number length
     * @return A random number
     */
    public static String generateRandomCode(int length){
        if (length < 0) {
            throw new IllegalArgumentException("random code length must be more than 0");
        }
        return (int)((Math.random() * 9 + 1)  * (Math.pow(10, length - 1))) + "";
    }
}
