package com.self.boot.algorithm.base;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

/**
 * 链表的基本算法：
 * 相邻交换 使用一个虚拟头节点 让这个节点的下一节点和下下一节点交换
 * 链表反转 递归反转
 * 链表求环
 */
public class LinkedTest {

    @Test
    public void mainTest() {
        // 1->2-> null
        ListNode node1 = tailInsert(5);

//         ListNode node = swapPairs(head);
//        ListNode node = reverseList0(node1);
        ListNode node = removeElements(node1, 5);
        printLinkedList(node);
    }


    @Test
    public void initTest() {
        ListNode node = tailInsert(5);
        printLinkedList(node);
        System.out.println("============================");
        ListNode node1 = headInsert(5);
        printLinkedList(node1);
    }

    private void printLinkedList(ListNode node) {
        ListNode p = node;
        while (p != null) {
            System.out.println(p.val);
            p = p.next;
        }
    }

    // 交换相邻的两位
    public ListNode swapPairs(ListNode head) {
        ListNode node0 = new ListNode(0, head);
        ListNode p = node0;
        while (p.next != null && p.next.next != null) {
            ListNode node3 = p.next.next.next;
            p.next.next.next = p.next;
            p.next = p.next.next;
            p.next.next.next = node3;
            p = p.next.next;
        }

        return node0.next;
    }


    //    输入: 1->2->3->4->5->NULL, m = 2, n = 4
//    输出: 1->4->3->2->5->NULL
    //    1 ≤ m ≤ n ≤ 链表长度  1 2
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (n == 1 || m == n) {
            return head;
        }

        // 0. 创建虚拟头节点0
        // 1. 先找m前一节点吧记录
        // 2. 找到m的下一节点 下下一节点在 在m-1 和 m 之间进行头擦法 m不动 让 n-m这些节点循环头插即可
        ListNode node0 = new ListNode(0, head);
        ListNode p = node0;
        int len = n - m;

        int i = 0;
        // 0->1->2->3->4->5->NULL
        while (++i < m) {
            p = p.next;
        }

        while (i <= n) {
            // 找到m前一节点
            while (++i < m) {
                p = p.next;
            }

            // 先找到 m-1节点
            ListNode m1 = p;

            p = p.next;

            i++;
        }

        return node0.next;
    }

    @Test
    public void removeNodeTest() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(1);
        linkedList.removeFirst();
//        linkedList.removeAll(Collections.singleton(1));
        System.out.println(linkedList);

    }

    // 链表至少包含两个节点。
    // 链表中所有节点的值都是唯一的。
    // 给定的节点为非末尾节点并且一定是链表中的一个有效节点。
    // 不要从你的函数中返回任何结果。
    public void deleteNode(ListNode node) {
        // 1->2->3-null  node(2, 3)
        // 获取这个节点的头节点 新颖
        node.val = node.next.val;
        node.next = node.next.next;
    }

    // 移除这个元素 --> easy
    public ListNode removeElements(ListNode head, int val) {
        ListNode node0 = new ListNode(val - 1);
        node0.next = head;
        ListNode p = node0; // 0->1->2->3->4->5->NULL
        while (p.next != null) {
            if (p.next.val == val) {
                ListNode node = p.next.next;
                p.next.next = null; // help gc ==>> node[i, null]
                p.next = node; // 直接接后面的
            } else {
                p = p.next;
            }
        }

        // 0-1-1-null
        return node0.next;
    }

    // 关系到二叉树的题目
    // 链表的题目一定要熟练
    // 简单的反转链表 普通方法 和 递归   1->2->3->4->5->NULL
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            // 总要返回一头节点
            return head;
        }

        ListNode subListNode = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return subListNode;
    }

    // 头插法 head  返回最新的头
    // tail尾插法 返回一个固定的头
    public ListNode reverseList0(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode p = head;
        // 将一个链表的节点遍历然后头插到一个新的链表形成一个新的链表
        ListNode newHead = new ListNode();
        while (p != null) {
            // 这个是不变的吧
            ListNode newSubHead = newHead.next;
            newHead.next = p;
            p = p.next;
            newHead.next.next = newSubHead;
        }

        return newHead.next;
    }

    // 尾插法
    public ListNode tailInsert(int n) {
        ListNode node = new ListNode();
        ListNode p = node;
        for (int i = 0; i <= n; i++) {
            p.next = new ListNode(i);
            p = p.next;
        }

        return node.next;
    }

    // 头插法
    public ListNode headInsert(int n) {
        ListNode node = new ListNode();
        for (int i = n; i >= 0; i--) {
            ListNode node1 = new ListNode(i);
            node1.next = node.next;
            node.next = node1;
        }

        return node.next;
    }


    // node struct
    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    '}';
        }
    }
}
