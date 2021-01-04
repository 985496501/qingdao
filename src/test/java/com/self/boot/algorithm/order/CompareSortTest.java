package com.self.boot.algorithm.order;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 比较排序
 */
public class CompareSortTest {

    @Test
    public void mainTest() {
        int[] a = new int[]{1, 5, 3, 7, 2, 9, 6, 3, 2, 7};
        System.out.println("bubbleSort: " + Arrays.toString(bubbleSortTest(a)));
        System.out.println("selectionSort: " + Arrays.toString(selectionSort(a)));
        System.out.println("insertionSort: " + Arrays.toString(insertionSort(a)));
    }

    /**
     * 比较排序之冒泡排序
     * O(nn) stable
     *
     * 1. 从第一个开始 两两和后面的比较
     * 2. i轮之后最后面的i数就是最大的
     * 3. 最大的就不需要参与比较了
     */
    public int[] bubbleSortTest(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    swap(a, j, j + 1);
                }
            }
        }

        return a;
    }

    /**
     * 选择排序
     * O(nn) stable
     *
     * 每次选中一个为默认最小的 从剩余里面选择一个最小的和它比较
     */
    public int[] selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = a[i];
            for (int j = i + 1; j < a.length; j++) {
                // 7   9987'6521 选择排序
                if (min > a[j]) {
                    swap(a, i, j);
                }
            }
        }

        return a;
    }


    /**
     * 插入排序
     * O(nn) stable
     *
     * 默认前面有序 然后后面每一个元素往前面有序的插入
     * 先把当前的拿出来保存下来 然后找到这个值在前面有序的位置
     * 当前值  有序序列的尾索引
     */
    public int[] insertionSort(int[] a) {
        int len = a.length;
        int preIdx, cur;
        for (int i = 1; i < len; i++) {
            preIdx = i - 1;
            cur = a[i];
            while (preIdx >= 0 && a[preIdx] > cur) {
                a[preIdx + 1] = a[preIdx];
                preIdx--;
            }
            a[preIdx + 1] = cur;
        }
        return a;
    }

    /**
     * 希尔排序 是插入排序的一种增强
     */
    public int[] shellSort(int[] a) {

        return a;
    }




    private void swap(int[] a, int l, int r) {
        int temp = a[l];
        a[l] = a[r];
        a[r] = temp;
    }
}
