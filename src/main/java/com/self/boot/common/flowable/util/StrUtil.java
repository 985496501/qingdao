package com.self.boot.common.flowable.util;

import org.springframework.util.StringUtils;

/**
 * 业务补充
 *
 * @author: jinyun
 * @date: 2021/1/21
 */
public class StrUtil extends StringUtils {

    /**
     * 传入的所有字符串都是非空 one != null && "".equal(one)
     * tested.
     *
     * @param many one two ...
     * @return true or false
     */
    public static boolean allNotEmpty(String... many) {
        if (many == null || many.length == 0) {
            return false;
        }

        for (int i = 0; i < many.length && hasLength(many[i]); i++) {
            if (i == many.length - 1) {
                return true;
            }
        }

        return false;
    }

//    public static void main(String[] args) {
//        System.out.println(allNotEmpty("adf", "fdsa", "null"));
//    }
}
