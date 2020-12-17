package com.self.boot.primitive;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringTest {


    @Test
    public void isMatchTest() {

    }

    /**
     *
     */
    public boolean isMatch(String s, String p) {
        // s, p * .

        return false;
    }


    @Test
    public void longestCommonPrefixTest() {
//        String[] str = new String[]{"flower","flow","flight"};
        String[] str = new String[]{"", "B"};
        String s = longestCommonPrefix(str);
        System.out.println(s);
    }

    // 最长公共子串
    // 把字符串一摆 然后ba
    public String longestCommonPrefix(String[] strs) {
        // ["flower","flow","flight"]
        if (strs.length == 0)
            return "";


        int minIdx = 0;
        int minLen = strs[0].length();
        for (int i = 0; i < strs.length; i++) {
            if (minLen > strs[i].length()) {
                minLen = strs[i].length();
                minIdx = i;
            }
        }

        String minStr = strs[minIdx];


        int i = 0;
        while (i < minStr.length()) {
            if (isSame(strs, minIdx, i)) {
                ++i;
            } else {
                break;
            }
        }

        return minStr.substring(0, i);
    }

    private boolean isSame(String[] strs, int minIdx, int i) {
        for (int j = 0; j < strs.length; j++) {
            if (minIdx != j) {
                if (strs[j].charAt(i) != strs[minIdx].charAt(i)) {
                    return false;
                }
            }
        }

        return true;
    }


    @Test
    public void charTest() {
        String str = "flower";
        int i = str.indexOf(1);
        System.out.println(i);

        char c = str.charAt(1);
        System.out.println(c);
    }

    // 查询最短的字符串
    @Test
    public void forTest() {
        String[] str = new String[]{"", "B"};
    }

    @Test
    public void threeSumTest() {

    }

    // 3数之和等于0
    @Test
    public List<List<Integer>> threeSum(int[] nums) {
        //Arrays.sort(nums);
        // brute solution: 组合 n有  C(3, n)种情况
        if (nums.length < 3) {
            return null;
        }

        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = 1; j < nums.length - 1; j++) {
                for (int k = 2; k < nums.length; k++) {
                    if (0 == nums[i] + nums[j] + nums[k]) {
                        List<Integer> item = Arrays.asList(nums[i], nums[j], nums[k]);
                        list.add(item);
                    }
                }
            }
        }


        return list;
    }

    // 返回匹配的第一个索引 实际上就是 String.indexOf()的默认实现
    public int strStr(String haystack, String needle) {
        char[] source = haystack.toCharArray();
        char[] target = needle.toCharArray();
        return indexOf(source, target);
    }

    public int indexOf(char[] source, char[] target) {
        // 小串的第一个字符
        char first = target[0];

        // abcde
        //    bc

        // 大串和小串的差
        int max = source.length - target.length;

        // 缩小遍历的次数 如果
        for (int i = 0; i <= max; i++) {
            /* Look for first character. */
            if (source[i] != first) {
                while (++i <= max && source[i] != first);
            }

            /* Found first character, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + target.length - 1;
                for (int k = 1; j < end && source[j] == target[k]; j++, k++);

                if (j == end) {
                    /* Found whole string. */
                    return i;
                }
            }
        }

        return -1;
    }




}
