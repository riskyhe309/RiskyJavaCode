import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by RISKYHE on 2014/5/9.
 */
public class WordBreak_1 {


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


            ArrayList<Integer> al = new ArrayList<Integer>();

            for (int i = s.length() - 1; i >= 0; i--) {

                String str = s.substring(i);


                if (dict.contains(str)) {
                    al.add(i);
                } else {
                    for (Integer n : al) {
                        if (dict.contains(s.substring(i, n))) {
                            al.add(i);
                            break;
                        }
                    }
                }
            }

            if (al.isEmpty()) {
                return false;
            } else {

                int last = al.get(al.size() - 1);

                return last == 0 ? true : false;
            }
        }
    }


}
