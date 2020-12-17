package com.self.boot.algorithm;

import org.junit.jupiter.api.Test;

import java.util.*;

public class StringTest {

    @Test
    public void strStrTest() {
        System.out.println(strStr("hello", "ll"));
    }

    // cabcd 4
    //   cd 2
    public int strStr(String haystack, String needle) {
        // 最多查找max
        int max = haystack.length() - needle.length();
        // 最先查找第一个字符
        char c = needle.charAt(0);
        for (int i = 0; i <= max; i++) {
            // 同一个索引查找字符
            while (i <= max) {
                if (haystack.charAt(i) == c) {
                    break;
                }
                i++;
            }

            if (i <= max) {
                String substring = haystack.substring(i, i + needle.length());
                if (needle.equals(substring)) {
                    return i;
                }
            } else {
                return -1;
            }
        }


        return -1;
    }



    //  s = "barfoothefoobarman",
    //  words = ["foo","bar"]
    //
    public List<Integer> findSubstring(String s, String[] words) {
        // 一个数组
        return  null;
    }


    static int indexOf(char[] source, char[] target) {
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


    @Test
    public void getLongestSubstringTest() {
        System.out.println("====>> 最终 au 的最大子串长度为：" + longestSubstring("au"));
    }


    @Test
    public void getLongestPalindromeTest() {
        System.out.println("longest palindrome:   " + longestPalindrome("aacabdkacaa"));
    }

    /**
     * 解题的思路:
     * fasfasdaaaaaaaaaaa
     * fdasfasabcddcba
     * fasdfasdmadam
     * <p>
     * 基本就是这 3 种情况 规定算法的方法就是问题解的方向
     * 奇数单核的可以处理  但是偶数双核就不好处理了
     * 对字符串进行 pre-handle
     * <p>
     * 一个指针从左向右一次移动 然后向两边扩散
     * 2k + 1 = odd        #1#2#3#2#
     */
    private String longestPalindrome(String s) {
        if (s == null) {
            return null;
        }

        char[] chars = s.toCharArray();
        char[] newChar = new char[chars.length * 2 + 1];
        int newP = 0;
        for (char aChar : chars) {
            newChar[newP] = '#';
            ++newP;
            newChar[newP] = aChar;
            ++newP;
        }

        newChar[newP] = '#';


        String longestPalindrome = "";
        // 从第二个开始 到 倒数 第二个
        for (int i = 1; i < newChar.length - 1; i++) {
            int p = 1;
            LinkedList<Character> linkedList = new LinkedList<>();
            linkedList.add(newChar[i]);
            // 定位的索引 +— 步长 必须在数组的索引内
            while (i - p >= 0 && i + p <= newChar.length - 1) {
                if (newChar[i - p] != newChar[i + p]) {
                    break;
                }

                linkedList.addFirst(newChar[i - p]);
                linkedList.addLast(newChar[i - p]);
                p++;
            }

            StringBuilder builder = new StringBuilder();
            for (Character character : linkedList) {
                if (!character.equals('#')) {
                    builder.append(character);
                }
            }

            longestPalindrome = longestPalindrome.length() > builder.toString().length() ? longestPalindrome : builder.toString();
        }

        return longestPalindrome;
    }


    @Test
    public void longestPalindrome2Test() {
        System.out.println(longestPalindrome2("cbbd"));
    }

    /**
     * 两种形式
     * <p>
     * madam
     * #a#b#b#a#
     * k --> 2k + 1
     * f(k) = 2k + 1
     */
    private String longestPalindrome2(String s) {
        if (s == null) {
            return null;
        }

        String[] newChar = s.replace("", "#").split("");
        String longest = "";
        // 从第二个开始 到 倒数第二个 [2, length - 1]  A#a#B
        for (int i = 1; i < newChar.length - 1; i ++) {
            StringBuilder stringBuilder = new StringBuilder(newChar[i]);
            int step = 1;
            while (i - step > -1 && i + step < newChar.length) {
                if (!newChar[i - step].equals(newChar[i + step])) {
                    break;
                }

                stringBuilder.insert(0, newChar[i - step]);
                stringBuilder.append(newChar[i - step]);
                step++;
            }

            String replace = stringBuilder.toString().replace("#", "");
            longest = replace.length() > longest.length() ? replace : longest;
        }

        return longest;
    }

    @Test
    public void convertTest() {

    }

    // todo:
    private String convert(String str, int numRows) {

        return "";
    }

    @Test
    public void stringTest() {
        String str = "abd";
        System.out.println(str.replace("", "#"));

        StringBuilder builder = new StringBuilder();
        builder.append("c");
        builder.insert(0, "a");
        System.out.println(builder.toString());
    }

    /**
     * for line data structure, do frequently write-ops
     */
    @Test
    public void linkedListTest() {

        // new Linked
        LinkedList<Character> linkedList = new LinkedList<>();
        linkedList.add('b');
        linkedList.addFirst('a');
        linkedList.addLast('c');


        System.out.println(Arrays.toString(linkedList.toArray()));
    }


    /**
     * // pwwkew  3
     * // dvdf 2
     * // au 2
     * // aaaa 1
     * // "abcabcbb" 3
     * <p>
     * approach:
     * 1> 如果这个字符串为null直接返回0 如果为"" 直接返回0 如果为"x" 直接返回1
     * 2> 定义一个最大长度变量 和 头尾双指针分别指向头指针
     * 3> f 先尝试右移1位 f1 是否在左子串中存在呢 存在则 h 要在存在的index+1
     *
     * @param str
     * @return
     */
    private int longestSubstring(String str) {
        if (str == null) {
            return 0;
        }

        if (str.length() < 2) {
            return str.length();
        }

        // 转成字符数组
        char[] chars = str.toCharArray();
        // 做大的长度默认为0 头和尾都是第一位
        int longest = 0, head = 0, foot = 0;
        while (foot < chars.length - 1) {
            // 右角标一致右移  不能让一个指针一值右移
            // 右指针 要尝试性 右移
            System.out.println("右指针尝试右移1位: " + chars[foot + 1]);
            System.out.println("左子串为: " + str.substring(head, foot + 1));

            // note: 不要使用子串的索引 因为它会把索引重置 搞乱
            // int charIndex = str.substring(head, foot + 1).indexOf(chars[foot + 1]);
            int charIndex = -1;
            for (int p = head; p < foot + 1; p++) {
                if (chars[foot + 1] == chars[p]) {
                    charIndex = p;
                }
            }

            if (charIndex > -1) {
                System.out.println("右字符在左子串的索引坐标: " + charIndex);
                head = charIndex + 1;
                System.out.println("左指针右移到右字符在左左子串索引+1的位置:" + head);
                if (head > foot) {
                    System.out.println("左指针大于右指针, 让右指针右移");
                    foot = head;
                }
            } else {
                System.out.println("右字符在左子串中不存在, 继续右移1位");
                foot++;
            }

            int temp = foot - head + 1;
            longest = Math.max(longest, temp);
            System.out.println("一次循环最大子串长度为: " + longest);
            System.out.println();
        }

        return longest;
    }
}
