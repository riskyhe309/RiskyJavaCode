/**
 * Created by RISKYHE on 2014/6/7.
 */
public class Solution {

    public RandomListNode copyRandomList(RandomListNode head) {

        RandomListNode p = head;

        RandomListNode copy = null;
        if (head == null)
            return copy;


        //Copy the node(mark A'), and make the next of A'(the copy of A) is the next of A,
        // then make the next of A is the A'
        while (p != null) {

            RandomListNode temp = new RandomListNode(p.label);
            temp.next = p.next;
            p.next = temp;
            p = temp.next;
        }


        //the random of A' is the next node of the random of A
        //
        // A    B     C         A.random = C
        //  \  / \   / \
        //   A'    B'   C'      A'.random = C' (C' is C.next)

        copy = head.next;

        p = head;                   //the first of origin list
        RandomListNode q = copy;    //the first of copy list

        while (q.next != null) {

            if (p.random != null) {
                q.random = p.random.next;
            }
            p = q.next;
            q = p.next;
        }
        //the last node
        if (p.random != null) {
            q.random = p.random.next;
        }


        //renew the links
        p = head;
        q = copy;

        while (q.next != null) {
            p.next = q.next;
            q.next = q.next.next;

            p = p.next;
            q = q.next;
        }
        p.next = null;
        q.next = null;


        return copy;
    }


}
