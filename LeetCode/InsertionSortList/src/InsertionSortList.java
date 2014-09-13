/**
 * Created by RISKYHE on 2014/5/5.
 */
public class InsertionSortList {

    public static void main(String[] args) {

        ListNode head =null;

        for(int i = 0 ;i<123;i++){

            ListNode q = new ListNode(i);
            q.next = head;
            head = q;
        }

        Solution sl = new Solution();

        head = sl.insertionSortList(head);

        while(head!=null){
            System.out.println(head.val);
            head = head.next;
        }

    }


    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) {
     * val = x;
     * next = null;
     * }
     * }
     */

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }


    public static class Solution {

        public ListNode insertionSortList(ListNode head) {


            if (head == null)
            {return  null;
            }

//            if (head.next == null){
//                return head;
//            }

            ListNode q = head.next;

            head.next = null;

            while (q != null) {

                ListNode t = q.next;


                ListNode m = head;
                ListNode n =null;
                while ( m != null && q.val > m.val) {
                    n = m;
                    m = m.next;
                }

                if (m == head){
                    head = q;
                    head.next = m;
                }
                else{

                    n.next = q;
                    q.next = m;
                }


                q = t;
            }



            return head;

        }
    }


}
