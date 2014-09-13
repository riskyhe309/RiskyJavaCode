/**
 * Created by RISKYHE on 2014/6/7.
 */
public class SingleNumber {
    public static void main(String[] args) {

        int[] A = new int[9];
        for (int i = 0; i < 9; i++) {
                A[i] = i/2;
        }
        Solution sl = new Solution();
        System.out.println(sl.singleNumber(A));
    }
}
