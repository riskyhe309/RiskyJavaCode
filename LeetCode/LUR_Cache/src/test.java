/**
 * Created by RISKYHE on 2014/5/7.
 */
public class test {
    public static void main(String[] args) {

        LRUCache lc = new LRUCache(2048);

        for (int i = 0; i < 10; i++) {
            lc.set(i, i * 5 + i * i -1);
        }

        for (int j = 1; j < 5; j++) {
            System.out.println(lc.get(j));
        }


    }
}
