/**
 * Created by RISKYHE on 2014/6/7.
 */
public class Solution {
    public int singleNumber(int[] A) {

        int result = 0;
        for (int i = 0; i < 32; i++) {

            int key = 0;
            for (int e : A) {
                key += (e >> i) & 1;    //store the ith bit
            }

            result |= ((key % 3) << i);

        }

        return result;
    }
}