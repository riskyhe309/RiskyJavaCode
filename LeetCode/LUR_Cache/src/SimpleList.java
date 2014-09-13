/**
 * Created by RISKYHE on 2014/6/8.
 */
public class SimpleList {

    Node head = null;



    public void delete(Node n) {
        if (n == head) {
            head = n.next;
        }

        n.next.pre = n.pre;
        n.pre.next = n.next;
    }

    public void insert(Node n) {
        if (head != null) {
            n.next = head;
            head.pre.next = n;
            n.pre = head.pre;
            head.pre = n;
            head = n;
        } else {
            head = n;
            n.pre = n;
            n.next = n;
        }
    }
}
