/**
 * Created by RISKYHE on 2014/6/7.
 */
public class CopyListRandom {

    public static void main(String[] args) {

        RandomListNode head = null;

        for (int i = 0; i < 12; i++) {

            RandomListNode q = new RandomListNode(i);
            q.next = head;
            head = q;
        }

        RandomListNode q = head;
        RandomListNode p = head;
        for (int j = 0; j < 6; j++) {

            if ( (j & 1)== 0) {

                q.random = p;
            }

            q = q.next;
            p = p.next.next;

        }


        Solution sl = new Solution();

        RandomListNode result = sl.copyRandomList(head);

        p = head;
        q = result;
        for (int i = 0; i < 12; i++) {
            if (p.random != null)
                System.out.println("The Origin:  " + p.random.label + "      DeepCopy:  " + q.random.label);
            System.out.println(p.label);
            p = p.next;
            q = q.next;
        }


    }
}
