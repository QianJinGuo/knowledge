package tech.jinguo.test.interview.algorithm;

/**
 * 反转链表（LeetCode #206）
 * 给你单链表的头节点 head，请你反转链表，并返回反转后的链表。
 *
 * 提供迭代和递归两种解法
 */
public class ReverseLinkedList {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * 迭代解法：时间复杂度 O(n)，空间复杂度 O(1)
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    /**
     * 递归解法：时间复杂度 O(n)，空间复杂度 O(n)（递归栈）
     */
    public ListNode reverseListRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseListRecursive(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    private static void printList(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        ListNode curr = head;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) sb.append(" -> ");
            curr = curr.next;
        }
        sb.append("]");
        System.out.println(sb);
    }

    public static void main(String[] args) {
        ReverseLinkedList solution = new ReverseLinkedList();

        // 构建链表 1 -> 2 -> 3 -> 4 -> 5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        System.out.print("原始链表: ");
        printList(head);

        ListNode reversed = solution.reverseList(head);
        System.out.print("迭代反转: ");
        printList(reversed);  // [5 -> 4 -> 3 -> 2 -> 1]

        // 重新构建链表
        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);
        head2.next.next = new ListNode(3);

        ListNode reversed2 = solution.reverseListRecursive(head2);
        System.out.print("递归反转: ");
        printList(reversed2); // [3 -> 2 -> 1]
    }
}
