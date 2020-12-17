package com.self.boot.tree;

import org.junit.jupiter.api.Test;

// binary-tree basic algorithm
// new Tree()
// retrieve 二叉树的前序 中序 后序 遍历算法
// auto-balance 二叉树的自平衡 和 自旋
public class BinaryTree {

    @Test
    public void mainTest() {
        // 使用数组作为树的存储结构
        Integer[] a = {1, 2, 2, 3, 3, null, null, 4, 4};
        // todo: how to solve ?
    }









    public TreeNode construct(Integer[] a) {
        return null;
    }


    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}


