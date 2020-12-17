package com.self.boot.algorithm;

import org.junit.jupiter.api.Test;

public class IntegerTest {

    /**
     * 将一个有符号整数反转输出返回
     */
    @Test
    public void reverseTest() {
        //  System.out.println(reverse(-100));
        System.out.println(reverse(1534236469));
    }

    /**
     * 数字相关的算法就是 / % 整除和取余
     * +0  -0
     * 123
     * 因为int的值有限。 1534236469  10亿
     * 123/10 = 12
     * 123%10 = 3
     * <p>
     * 1
     * 2
     *
     * @param x source
     * @return target
     */
    private int reverse(int x) {
        long n = 0;
        while (x != 0) {
            n = n * 10 + x % 10;
            x = x / 10;
        }

        // 保证不越界
        return (int) n == n ? (int) n : 0;
    }


    @Test
    public void charTest() {
        char z1 = '1';
        char z2 = 'a';
        char z3 = '0';
        System.out.println((int) z1);
        System.out.println((int) z2);
        System.out.println((int) z3);

    }


    @Test
    public void myAtoiTest() {

    }

    @Test
    public void isPalindromeTest() {
        System.out.println(isPalindrome(121));
        System.out.println(isPalindrome(1221));
        System.out.println(isPalindrome(12212326));
    }

    // 121  1221
    public boolean isPalindrome(int i) {
        String s = String.valueOf(i);
        int p = 0, q = s.length() - 1;
        while (p <= q) {
            if (q == p)
                return s.charAt(p) == s.charAt(q);

            if (s.charAt(p) == s.charAt(q)) {
                p++;
                q--;
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * 使用计算来判断
     */
    public boolean isPalindrome2(int i) {
        //
        int n = 0;

        return true;
    }
}
