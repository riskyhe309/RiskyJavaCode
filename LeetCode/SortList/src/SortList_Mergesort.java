/**
 * Created by RISKYHE on 2014/5/6.
 */
public class SortList_Mergesort {


    public static void main(String[] args) {
        ListNode head =null;

        for(int i =0  ;i<13;i++){

            ListNode q = new ListNode(i);
            q.next = head;
            head = q;
        }

        Solution sl = new Solution();

        head = sl.sortList(head);

        while(head!=null){
            System.out.println(head.val);
            head = head.next;
        }
    }





    public static class Solution {
        public ListNode sortList(ListNode head) {

            if (head == null || head.next == null )
                return head;

            //find the middle node
            ListNode first = head;
            ListNode second = head;
            while (first.next !=null && first.next.next !=null){
                first = first.next.next;
                second = second.next;
            }


            //the first part and the second part
            first = second;
            second = second.next;
            //cat the first link
            first.next =null;
            first = sortList(head);

            //the second part
            second = sortList(second);

           return merg(first, second);
        }

        private ListNode merg(ListNode first, ListNode second) {
            ListNode mergeLink = null;

            if (first == null) return second;
            if (second == null) return  first;

            if (first.val < second.val){
                mergeLink = first;
                first = first.next;
            }
            else{
                mergeLink = second;
                second = second.next;
            }

            mergeLink.next = null;

            ListNode p = mergeLink;


            while (first != null && second !=null){

                if (first.val < second.val){
                    p.next = first;
                    first = first.next;
                }
                else{
                    p.next = second;
                    second = second.next;
                }

                p = p.next;

            }
            if (first != null){
                p.next = first;
            }
            else{
                p.next = second;
            }


            return  mergeLink;
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
