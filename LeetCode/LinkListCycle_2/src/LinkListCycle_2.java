import java.util.ArrayList;

/**
 * Created by RISKYHE on 2014/5/7.
 */
public class LinkListCycle_2 {

    public static void main(String[] args) {



        ListNode tail = new ListNode(1);
        ListNode head = new ListNode(2);

        head.next = tail;
//        for (int i = 0; i < 10; i++) {
//            ListNode q = new ListNode(i);
//            q.next = head;
//            head = q;
//            if (i == 5) {
//                t = q;
//            }
//        }



        Solution sl = new Solution();

//        while(head != null){
//            System.out.println(head.val);
//        }
        System.out.println(sl.detectCycle(head));

    }


    public static class Solution {

        public ListNode detectCycle(ListNode head) {


            if (head == null || head.next == null) {
                return null;
            }


            ListNode p = head;
            ListNode q = head;

            // if this has a cycle the the p
            // must catch up with q in the cycle.
            do {

                if (q.next == null || q.next.next == null){
                    return null;
                }
                p = p.next;
                q = q.next.next;

                if (p == q) {
                    break;
                }

            } while (p !=q);


            /*
             It is a famous known problem ：Hare and Tortoise 。
             Length of head to cycle started node:x 。Length of the cycle: y 。
             Let hare run two steps while tortoise runs one step。
             while both of them entered the cycle, the hare is definitely to overlap the tortoise at some node, we define it as m:
             The hare totally runs: x + ky + m The tortoise totally runs: x + ty + m，
             Thus, ky = 2ty + x + m 。we have (x + m) mod y = 0
             We can conclude that if the hare run more x steps（make it run one step）, it will reach the cycle's starting node.
            */

            p = head;
            while (p != q) {

                q = q.next;
                p = p.next;
            }


            return p;
        }
    }


    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }


}
