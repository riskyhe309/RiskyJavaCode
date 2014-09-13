/**
 * Created by RISKYHE on 2014/5/6.
 */
public class ReverseWords {

    public static void  main(String[] args){

        String s = "   a   b ";

        Solution sl = new Solution();

        s =sl.reverseWords(s);

        System.out.println(s);
    }






    public static class Solution {
        public String reverseWords(String s) {

            StringBuffer result = new StringBuffer();

            s= s.trim();

            String [] str = s.split("\\s+");

            for (String tempStr : str){
                result.insert(0, tempStr+" ");
            }

            return result.toString().trim();

        }
    }

}
