

/**
 * Created by RISKYHE on 2014/5/7.
 */
public class ReorderList {

    public static void main(String[] args) {

        ListNode head = null;

        for (int i = 0; i < 12; i++) {

            ListNode q = new ListNode(i);
            q.next = head;
            head = q;
        }

        System.out.println(" the list in begin");

        ListNode w = head;
        while (w != null) {
            System.out.println(w.val);
            w = w.next;
        }


        Solution sl = new Solution();

        sl.reorderList(head);

        System.out.println(" the list reorder");
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }

    }


    public static class Solution {
        public void reorderList(ListNode head) {

            ListNode fast = head;
            ListNode slow = head;

            if (head == null || head.next == null) {
                return ;
            }
            while (fast.next != null && fast.next.next != null) {

                fast = fast.next.next;
                slow = slow.next;
            }

            //split the list;
            fast = slow.next;
            slow.next = null;

            //reverse the later list
            fast = reverseList(fast);

            ListNode m = head;
            ListNode n = fast;
            while (n != null && m != null) {

                ListNode nNext = n.next;
                ListNode mNext = m.next;

                //insert the reverseList
                m.next = n;
                n.next = mNext;

                m = mNext;
                n = nNext;

            }
        }

        private ListNode reverseList(ListNode head) {


            if (head == null || head.next == null) {
                return head;
            }

            ListNode tHead = null;
            ListNode p = head;
            ListNode q = head.next;

            while (p.next != null) {
                p.next = tHead;
                tHead = p;
                p = q;
                q = q.next;
            }

            p.next = tHead;
            tHead = p;

            return tHead;
        }
    }





    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
