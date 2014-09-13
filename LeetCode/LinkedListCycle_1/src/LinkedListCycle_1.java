/**
 * Created by RISKYHE on 2014/5/7.
 */
public class LinkedListCycle_1 {

    public static void main(String[] args) {

        ListNode head = null;

        ListNode t = null;

        for (int i = 0; i < 2; i++) {

            ListNode q = new ListNode(i);
            q.next = head;
            head = q;
            if (i == 0){
                t = head;
            }
        }

       // t.next = head;
        Solution sl = new Solution();


        System.out.println(sl.hasCycle(head));

    }


    public static class Solution {

        public boolean hasCycle(ListNode head) {

            if (head == null || head.next == null) {
                return false;
            }


            ListNode p = head;
            ListNode q = head;

            // if this has a cycle the the p
            // must catch up with q in the cycle.

            while (q.next != null && q.next.next != null) {

                p = p.next;
                q = q.next.next;

                if (p == q) {
                    return true;
                }

            }
            return false;
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
