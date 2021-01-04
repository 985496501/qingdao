package com.self.boot.algorithm.base;

import org.junit.jupiter.api.Test;

import java.util.*;

public class ArrayTest {

    @Test
    public void mainTest() {
//        int[] a = {1, 2, 4, 6, 7, 9, 50};
//        System.out.println(removeDuplicates(a));
//
//        int[] b = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
//        System.out.println(removeElement(b, 1));

//        int[] n = {1, 3, -1, -3, 5, 3, 6, 7};
        int[] n = {1, 2, 3, 1, 1};
//        int[] ints = productExceptSelf(n);
//        System.out.println(Arrays.toString(ints));

        // [1,3,-1,-3,5,3,6,7]
        //3
//        int[] ints1 = maxSlidingWindow(n, 2);
//        System.out.println(Arrays.toString(ints1));

        System.out.println("majority: " + majority(n));
        System.out.println("" + containsDuplicateOptimised(n));
    }

    // nums = [0,0,1,1,1,2,2,3,3,4]   0, 1, 2, 3, 4
    // 给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
    // 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
    public int removeDuplicates(int[] nums) {
        if (nums.length < 2)
            return nums.length;

        int i = 1;
        int p = 1;
        while (i < nums.length) {
            if (nums[i] != nums[i - 1]) {
                nums[p] = nums[i];
                p++;
            }
            i++;
        }

        return p;
    }


    // time_complexity = O(n)  && space_complexity = O(1) 除了自己之外的数的和
    public int[] productExceptSelf(int[] nums) {
        int[] p = new int[nums.length];
        //  not understand
        // loop twice:  multiply left * multiply right
        int left = 1;
        int right = 1;

        // multiply left
        for (int i = 0; i < nums.length; i++) {
            p[i] = left;
            left *= nums[i];
        }

        // multiply right
        for (int i = nums.length - 1; i >= 0; i--) {
            p[i] *= right;
            right *= nums[i];
        }

        return p;
    }


    // time out. 这种方法可以算出但是 冗余
    public int[] productExceptSelf0(int[] nums) {
        int[] p = new int[nums.length];
        int index = 0;
        while (index < nums.length) {
            int pr = 1;
            int left = index - 1;
            int right = index + 1;
            while (left >= 0) {
                pr *= nums[left];
                left--;
            }

            while (right < nums.length) {
                pr *= nums[right];
                right++;
            }

            p[index] = pr;
            index++;
        }

        return p;
    }


    // 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
    //不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
    //元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素
    public int removeElement(int[] nums, int val) {
        int p = 0;
        int q = 0;
        while (p < nums.length) {
            if (nums[p] != val) {
                nums[q] = nums[p];
                q++;
            }

            p++;
        }

        return q;
    }

    // 返回滑动窗口中最大的值 (n-k+1)*k = nk k位常数  O(nk)  brute-force solution, interview fails.
    public int[] maxSlidingWindow0(int[] nums, int k) {
        int[] r = new int[nums.length - k + 1];
        // 234
        for (int i = 0; i < r.length; i++) {
            int max = nums[i];
            int j = i;
            while (j < i + k) { // j+0  j+1 ... j+k-1  k个
                max = Math.max(max, nums[j]);
                j++;
            }

            r[i] = max;
        }

        return r;
    }

    public int[] maxSlidingWindow2(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return nums;
        }

        int[] r = new int[nums.length - k + 1];
        // 双向队列 必须
        // 一个队列存储值的索引 让其由大到小排序
        // 首先一个过程是滑动窗口 right++ 让其不断到右边界
        // 到达右边界的时候这时候 应该让队列的头的值(max) 到咱们的返回数组中去是吧
        // 那么就要求每次遍历数组的时候 如果队列中的值要 就要弹出去 只保留相对较大的值的索引
        // 如果是递减数组那么, 队列的
        Deque<Integer> deque = new LinkedList<>();

        // 第一过程让窗口形成
//        int i;
//        for (i = 0; i < k; i++) {
//            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
//                deque.pollLast();
//            }
//
//            deque.offer(i);
//        }
//
//        // i = k - 1
//        r[0] = nums[deque.peek()];

        // k
        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }

            deque.offer(i);
            // 如果队列首的已经不在当前窗口内就要移除把
            if (deque.peek() <= i - k) {
                deque.poll();
            }

            // k相当于第二个数 i = 1的情况 i - k + 1 i>k
            if (i >= k - 1) {
                r[i - k + 1] = nums[deque.peek()]; // ?????? has bugs.
            }
        }

        return r;
    }

    // efficient algorithm 没有  那就是面试终结者

    // 仅仅靠一些crud是进不去那个薪资水平的.  其实就是固定的套路 要训练以前没有的思维才行
    // 然后用这种思维解决其他类似的问题
    // 处理前 k 个元素，初始化双向队列。
    // 双端队列实现滑动窗口最大值 这个队列的可以实现O(1)的复杂度完成添加删除和查找
    //
    //遍历整个数组。在每一步 :
    //
    //清理双向队列 :
    //
    //  - 只保留当前滑动窗口中有的元素的索引。
    //
    //  - 移除比当前元素小的所有元素，它们不可能是最大的。
    //将当前元素添加到双向队列中。
    //将 deque[0] 添加到输出中。
    //返回输出数组。
    // 返回滑动窗口中最大的值 线性时间复杂度解决
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return nums;
        }

        // 双向队列 必须
        Deque<Integer> deque = new LinkedList<>();
        int[] r = new int[nums.length - k + 1];
        // 先找到第一个最大的值 前k个  0==>k-1
        for (int i = 0; i < nums.length; i++) {
            // 将当前的值插入队列的递减序列 如果有小的就出来
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }

            deque.offer(i);
            // 54321  543 k =3  i = 3
            //  123456  k=2
            //   23456

            // 如果队列的头的索引已经不在[i, i+k)内就出列
            // i ... i+k-1
            if (deque.peek() == i - k) {
                deque.poll();
            }

            // 当队列最大k时
            if (i + 1 >= k) {
                r[i - k + 1] = nums[deque.peek()];
            }
        }

        return r;
    }

    // 优化算法
    public boolean containsDuplicateOptimised(int[] nums) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            int origin = set.size();
            set.add(nums[i]);
            if (origin == set.size()) {
                return true;
            }
        }


        return false;
    }


    // 至少出现两次为true timeout Time_C = O(n*n)
    public boolean containsDuplicate(int[] nums) {
        return Arrays.stream(nums).distinct().count() != nums.length;
    }


    // 二分查找
    public int binarySearch(int[] a, int target) {
        if (a.length == 0) {
            return -1;
        }

        // 从数组的头指针 尾指针 中间指针
        int min = 0, max = a.length - 1;
        while (min <= max) {
            int mid = min + ((max - min) >>> 1);
            if (a[mid] < target) {
                min = ++mid;
            } else if (a[mid] > target) {
                max = --mid;
            } else {
                return mid;
            }
        }

        return -(min + 1);
    }


    // 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数。
    //
    //进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？

    /**
     * median:
     * required: m + n digits
     * Time Complexity: O(log(m+n))
     */
    @Test
    public void findMedianSortedArraysTest() {
        int[] a = {1, 2};
        int[] b = {3, 4};
        double median = findMedianSortedArrays(a, b);
        System.out.println(median);
    }


    /**
     * 要求时间复杂度 就定义4个指针 compare
     * 解析：
     * a.length < 2 && b.length < 2     median = (a.length + b.length) / 2
     * <p>
     * 处理数组 a 的逻辑 == 2
     * p1 _> 0 p2 _> length - 1
     * <p>
     * p1 <= p2
     * <p>
     * <p>
     * <p>
     * {1, 2, 3, 4}
     * {3, 4, 5}
     * <p>
     * 234
     * 34
     * <p>
     * 34
     * 3
     * <p>
     * 4
     * 3
     */
    private double findMedianSortedArrays(int[] a, int[] b) {
        int[] c = mergeSortedArraysTest(a, b);
        if ((c.length & 1) == 1) {
            return c[(c.length - 1) >> 1];
        } else {
            return (c[c.length >> 1] + c[(c.length >> 2) - 1]) / 2.0;
        }
    }


    @Test
    public void mergeSortedArraysTest() {
        int[] a = {1, 2, 3, 4};
        int[] b = {3, 4, 5};
        int[] ints = mergeSortedArraysTest(a, b);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    /**
     * 把两个有序数组非空归并成一个有序数组
     * 如果底层数据结构是链表 该怎么操作 其实都是一样的
     */
    private int[] mergeSortedArraysTest(int[] nums1, int[] nums2) {
        if (nums1.length == 0) {
            return nums2;
        }

        if (nums2.length == 0) {
            return nums1;
        }


        // ordered arrays to merge one.
        int[] c = new int[nums1.length + nums2.length];
        int p = 0, q = 0, z = 0;
        while (z < c.length) {
            if (p < nums1.length && q < nums2.length) {
                if (nums1[p] > nums2[q]) {
                    c[z] = nums2[q];
                    q++;
                } else {
                    c[z] = nums1[p];
                    p++;
                }
            } else if (p < nums1.length) {
                c[z] = nums1[p];
                p++;
            } else {
                c[z] = nums2[q];
                q++;
            }

            ++z;
        }


        return c;
    }


    /**
     * Proof: > n/3  观察最大就俩  大于n/2的有且仅有一个 大于n/3的最多仅有两个
     * 大于 n/k 的有几个呢 证明 max k-1
     * assume that: k个众数
     * m>n/k>0, n, k 都是正整数
     * k * m > k * n/k = n   ===>> exist at most k-1个数
     * 数学的证明基础
     *
     * 时间复杂度O(1) once loop
     * 空间复杂度O(n) constant
     *
     * problem: 判断的条件过多 也不至于很快
     */
    public List<Integer> majorityElement(int[] nums) {
        // nums not null
        if (nums.length < 2) {
            return Collections.singletonList(nums[0]);
        }

        int candidate0 = 0;
        int count0 = 0;
        int candidate1 = 0;
        int count1 = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (candidate0 == nums[i]) {
                count0++;
                continue;
            }

            if (candidate1 == nums[i]) {
                count1++;
                continue;
            }

            if (count0 == 0) {
                candidate0 = nums[i];
                count0++;
                continue;
            }

            if (count1 == 0) {
                candidate1 = nums[i];
                count1++;
                continue;
            }

            count0--;
            count1--;
        }

        count0 = 0; count1 = 0;
        for (int num : nums) {
            if (num == candidate0) {
                count0++;
            } else if (num == candidate1){
                // else if
                count1++;
            }
        }

        if (count0 > nums.length / 3) {
            list.add(candidate0);
        }

        if (count1> nums.length / 3) {
            list.add(candidate1);
        }

        return list;
    }

    // 选择众数 摩尔投票法 你可以假设数组是非空的，并且给定的数组总是存在多数元素
    // 这个是最基本的摩尔投票法
    //

    /**
     * 最基本的摩尔投票法
     *
     * 前提是 一定存在这样一个数 是大于n/2的
     */
    public int majority(int[] nums) {
        int digit = nums[0];
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (digit == nums[i]) {
                count++;
            } else {
                if (count > 0) {
                    count--;
                } else {
                    digit = nums[i];
                    count++;
                }
            }
        }

        return digit;
    }
}
