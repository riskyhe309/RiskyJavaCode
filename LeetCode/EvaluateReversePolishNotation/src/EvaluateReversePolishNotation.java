import java.util.Stack;

/**
 * Created by RISKYHE on 2014/5/6.
 */
public class EvaluateReversePolishNotation {

    public static void main(String[] args) {

        String[] s = {"4", "13", "5", "/", "+"};


        Solution sl = new Solution();


        System.out.println(sl.evalRPN(s));

    }




    public static class Solution {
        public int evalRPN(String[] tokens) {

            Stack<Integer> stack = new Stack<Integer>();

            for (String s:tokens){

                if (s.equals("+")){
                    Integer a = stack.pop();
                    Integer b = stack.pop();
                    b = a + b;
                    stack.push(b);
                    continue;
                }

                if (s.equals("-")){
                    Integer a = stack.pop();
                    Integer b = stack.pop();
                    b = b - a;
                    stack.push(b);
                    continue;
                }

                if (s.equals("*")){
                    Integer a = stack.pop();
                    Integer b = stack.pop();
                    b = a * b;
                    stack.push(b);
                    continue;
                }

                if (s.equals("/")){
                    Integer a = stack.pop();
                    Integer b = stack.pop();
                    b = b / a;
                    stack.push(b);
                    continue;
                }

                Integer temp = Integer.valueOf(s);
                stack.push(temp);
            }

            return stack.pop().intValue();
        }
    }




}
