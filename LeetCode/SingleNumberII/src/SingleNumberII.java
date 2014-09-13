/**
 * Created by RISKYHE on 2014/6/7.
 */
public class SingleNumberII {

    public static void main(String[] args) {
        int[] A = new int[19];
        for (int i = 0; i < 19; i++) {
            A[i] = i / 3;
        }
        Solution sl = new Solution();

        System.out.println(sl.singleNumber(A));
    }
}


