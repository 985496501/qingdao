package com.self.boot.primitive;

import org.junit.jupiter.api.Test;

public class ArrayTest {
    @Test
    public void maxAreaTest() {
        int[] a = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int i = maxArea(a);
        int j = maxAreaOptimised(a);
        System.out.println(i); // expected: 49
        System.out.println(j);
    }

    // 这个可以的 brute
    public int maxArea(int[] height) {
        int max = 0;
        for (int i = 0; i < height.length - 1; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int area = Math.min(height[i], height[j]) * (j - i);
                max = Math.max(max, area);
            }
        }

        return max;
    }

    // width decrement 1, depends on height, which is higher
    // optimised  dynamic programming
    public int maxAreaOptimised(int[] height) {
        int max = 0;
        int p = 0, q = height.length - 1;
        while (p < q) {
            int area = Math.min(height[p], height[q]) * (q - p);
            max = Math.max(max, area);

            if (height[p] < height[q])
                p++;
            else
                q--;
        }

        return max;
    }
}
