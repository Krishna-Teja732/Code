package Leet.DataStructures;

import java.util.Stack;

public class MyQueue {
    private Stack<Integer> stk;
    private Stack<Integer> temp;

    public MyQueue() {
        stk = new Stack<>();
        temp = new Stack<>();
    }

    public void push(int x) {
        stk.push(x);
    }

    public int pop() {
        while(!stk.isEmpty()) temp.push(stk.pop());
        int val = temp.pop();
        while(!temp.isEmpty()) stk.push(temp.pop());
        return val;
    }

    public int peek() {
        return stk.peek();
    }

    public boolean empty() {
        return stk.isEmpty();
    }
}