package com.self.boot.primitive;

import org.junit.jupiter.api.Test;

public class PrimitiveTest {
    /**
     * 移位运算都是补码
     * 正数的补码 == 原码
     * 负数的补码 == 原码取反  低位+1
     * << 无符号左移 低位补0
     * >> 有符号右移 正数补0 负数补1
     * >>> 无符号右移 高位补0
     */
    @Test
    public void twoTest() {
        // left 左移 right 位
        System.out.println(1 << 1);
        System.out.println(1 << 2); // 4

        System.out.println(1 << 16); // 65536

        System.out.println(2 << 1);

        // 100 --> 010   2
        System.out.println(4 >>> 1);


        System.out.println(1 << 30); // 10亿多
    }

    @Test
    public void isEvenTest() {
        // 判断偶数使用 位运算 是最快的
        int random = 23432;

        // 最简单的 就是 直接除以 2 看是否整除 偶数的定义就是2可以整除
        if (random % 2 == 0) {
            System.out.println("this is a even");
        } else {
            System.out.println("this is a odd");
        }


        // 位运算 是最快的
        // 偶数的最低位一定是0   & and   都是1才是1
        if ((random & 1) == 1) {
            System.out.println("this is a odd");
        } else {
            System.out.println("this is a even");
        }
    }


    @Test
    public void powerTest() {
        // 判断一个数是2的几次幂 用无符号右移 看看循环几次
        // 2的次幂数  有且只有一位是1
        // 2 right move 1  == 1
        // 4 right move 2  == 1
        System.out.println(getPower(1024));
        System.out.println(getPower(512));
        System.out.println(getPower(1));
        System.out.println(getPower(-2));


        System.out.println(fetchPower(1024));
        System.out.println(fetchPower(512));
        System.out.println(fetchPower(1));
        System.out.println(fetchPower(-2));
    }

    /**
     * 移位运算相当舒服
     *
     * @param num 这个数一定是2的次幂
     * @return 幂数
     */
    public int getPower(int num) {
        if (num < 1) {
            return -1;
        }

        if (num == 1) {
            return 0;
        }

        int temp = 0;
        while (num != 1) {
            num = num >> 1;
            temp++;
        }

        return temp;
    }

    public int fetchPower(int num) {
        if (num < 1) {
            return -1;
        }

        if (num == 1) {
            return 0;
        }

        int temp = 1;
        int power = 0;
        while (num != temp) {
            temp = temp << 1;
            power++;
        }

        return power;
    }


}
