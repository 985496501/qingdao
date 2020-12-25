package com.self.boot.structure;

import java.util.Deque;
import java.util.LinkedList;

// 使用队列自定义栈
// 队列 FIFO
// 栈 LIFO
public class MyStack {
    // indefinite queue
    private final Deque<Integer> queue;
    /**
     * Initialize your data structure here.
     */
    public MyStack() {
        this.queue = new LinkedList<>();
    }

    /**
     * Push element x onto stack.
     */
    public void push(int x) {
        queue.add(x);
    }

    /**
     * Removes the element on top of the stack and returns that element.
     */
    public int pop() {
        return queue.pollLast();
    }

    /**
     * Get the top element.
     */
    public int top() {
        return queue.peekLast();
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return queue.size() == 0;
    }
}
