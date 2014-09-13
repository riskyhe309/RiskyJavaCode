/**
 * Created by RISKYHE on 2014/5/9.
 */
public class ValidNumber {


    public static void main(String[] args) {

        Solution sl = new Solution();

        // int[][] a = {{1, 3, 5,7}, {10, 11, 16,20}, {23, 30, 34,50}};

        //int[][] a = {{1},{3}};

//        int[] a = {0, 3, 5, 6, 7, 8};
//        int[] b = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        String num = " 005047e+6";
        System.out.println(sl.isNumber(num));

    }


    public static class Solution {
        public boolean isNumber(String s) {

            s = s.trim();

            char[] str = s.toCharArray();

            int length = str.length;

            if (length == 0) {
                return false;
            }

            int point = 0;

            boolean hasNum = false;  // judge if this string has num

            int start = 0;
            if (str[0] == '+' || str[0] == '-') {
                start++;
            }


            for (int i = start; i < str.length; i++) {

                if (str[i] == '.') {

                    point++;

                    if (!hasNum && i == length - 1) {   //exclude like "-."
                        return false;
                    }
                    if (point > 1) {   //if there two point this s is not num
                        return false;
                    }
                    continue;
                }

                if (str[i] == 'e') {


                    String tempStr = s.substring(i+1);
                    if (tempStr.contains(".") || tempStr.contains("e")){
                        return false;
                    }
                    if (!hasNum) {  // exclude "e41515"
                        return false;
                    }

                    return isNumber(tempStr);
                }


                if (str[i] > '9' || str[i] < '0') {  //not num
                    return false;
                } else {
                    hasNum = true;
                }

            }

            if (!hasNum) {      //has no num
                return false;
            }
            return true;
        }
    }
}
