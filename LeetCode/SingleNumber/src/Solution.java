/**
 * Created by RISKYHE on 2014/6/7.
 */
public class Solution {
    public int singleNumber(int[] A) {

        int num = 0;
        for (int i : A) {
            num ^= i;
        }
        return num;
    }
}