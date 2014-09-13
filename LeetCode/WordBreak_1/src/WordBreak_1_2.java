import java.util.HashSet;
import java.util.Set;

/**
 * Created by RISKYHE on 2014/5/9.
 */
public class WordBreak_1_2 {


    public static void main(String[] args) {

        Solution sl = new Solution();

        // int[][] a = {{1, 3, 5,7}, {10, 11, 16,20}, {23, 30, 34,50}};

        //int[][] a = {{1},{3}};

//        int[] a = {0, 3, 5, 6, 7, 8};
//        int[] b = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        String s = "leetcode";
        Set<String> st = new HashSet<String>();
        st.add("leet");
        st.add("code");
        System.out.println(sl.wordBreak(s, st));

    }


    public static class Solution {
        public boolean wordBreak(String s, Set<String> dict) {


            boolean[] hasBreak = new boolean[s.length() + 1];

            hasBreak[0] = true;
            for (int i = 1; i <= s.length(); i++) {
                for (int j = 0; j < i; j++) {
                    if (hasBreak[j] && dict.contains(s.substring(j, i))) {
                        hasBreak[i] = true;
                        break;
                    }
                }

            }

            return hasBreak[s.length()];
        }
    }


}
