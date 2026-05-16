package tech.jinguo.test.interview.datastructure;

import java.util.Stack;

/**
 * 最小栈（LeetCode #155）
 * 设计一个支持 push、pop、top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * 思路：使用辅助栈同步记录当前最小值，时间复杂度 O(1)
 */
public class MinStack {

    /** 主栈：存储实际元素 */
    private final Stack<Integer> stack;
    /** 辅助栈：栈顶始终是当前所有元素中的最小值 */
    private final Stack<Integer> minStack;

    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        stack.push(val);
        // 辅助栈压入当前最小值
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        } else {
            minStack.push(minStack.peek());
        }
    }

    public void pop() {
        stack.pop();
        minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin()); // -3
        minStack.pop();
        System.out.println(minStack.top());    // 0
        System.out.println(minStack.getMin()); // -2
    }
}
