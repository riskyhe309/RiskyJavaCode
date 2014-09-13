/**
 * Created by RISKYHE on 2014/5/6.
 */
public class SortList_Heapsort {


    public static void main(String[] args) {
        ListNode head = null;

        for (int i = 0; i < 13; i++) {

            ListNode q = new ListNode(i-10);
            q.next = head;
            head = q;
        }

        Solution sl = new Solution();

        head = sl.sortList(head);

        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }


    public static class Solution {
        public ListNode sortList(ListNode head) {

            if (head == null || head.next == null )
                return head;



            return head;
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
