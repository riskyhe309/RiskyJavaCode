import java.util.ArrayList;

/**
 * Created by RISKYHE on 2014/5/5.
 */
public class PalindromePartition {


    public static void main(String[] args) {

        String str1 = "ab";

        Solution sl = new Solution();

        ArrayList<ArrayList<String>> als = sl.partition(str1);


        for (int i = 0; i < als.size(); i++) {
            ArrayList<String> al = als.get(i);

            for (int j = 0; j < al.size(); j++) {

                System.out.print(al.get(j) + " ");
            }

            System.out.println("");
        }

    }


    public static class Solution {


        public ArrayList<ArrayList<String>> partition(String s) {

            ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

            getPartion(s, result);

            return result;
        }

        private void getPartion(String s, ArrayList<ArrayList<String>> result) {


            if (s.length() == 0){
                ArrayList<String> r = new ArrayList<String>();
                //r.add(s);
                result.add(r);
                return;
            }


            for (int i = 0; i <= s.length(); i++) {

                String tempStr = s.substring(0, i);

                if (!isPalindrome(tempStr)) {
                    continue;
                }

                String tempRest = s.substring(i, s.length());
                ArrayList<ArrayList<String>> rest = new ArrayList<ArrayList<String>>();

                getPartion(tempRest, rest);

                for (ArrayList<String> tempAl : rest){
                    tempAl.add(0,tempStr);
                    result.add(0,tempAl);
                }

//                for (int j = 0; j < rest.size(); j++) {
//
//                    rest.get(j).add(tempStr);
//                    result.add(rest.get(j));
//
//                }


            }
        }


        private boolean isPalindrome(String str) {

            if (str.length() == 0) {
                return false;
            }
            boolean isPd = true;
            int left = 0;
            int right = str.length() - 1;

            while (left < right) {

                if (str.charAt(left) != str.charAt(right)) {
                    break;
                }

                left++;
                right--;
            }
            if (left < right) {
                isPd = false;
            }
            return isPd;
        }
    }
}
