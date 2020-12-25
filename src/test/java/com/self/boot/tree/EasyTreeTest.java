package com.self.boot.tree;

import org.junit.jupiter.api.Test;

public class EasyTreeTest {

    @Test
    public void mainTest() {
        //
    }


    // 反转二叉树
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        // 使用递归 递归让算法变得简单
        TreeNode left = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(left);
        return root;
    }


    // tree的数据结构
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
