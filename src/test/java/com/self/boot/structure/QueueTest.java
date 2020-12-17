package com.self.boot.structure;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class QueueTest {

    @Test
    public void arrayDequeTest() {
        // double-ended queue 双端队列
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        System.out.println(deque);
        if (deque.size() > 0) {
            Integer first = deque.getFirst();
            System.out.println(first);

            Integer integer = deque.removeFirst();
            System.out.println(integer);
        }

    }

    @Test
    public void andTest() {
        int i = -1 & 15;  // 010000 01111 10001 01111
        System.out.println(i);
    }


    @Test
    public void linkedListTest() {
        // peek poll remove add
        Deque<Integer> list = new LinkedList<>();
        list.addLast(1);
        Integer peek = list.peek();
        System.out.println(peek);
        Integer integer = list.pollLast();
        System.out.println(integer);
    }


}
