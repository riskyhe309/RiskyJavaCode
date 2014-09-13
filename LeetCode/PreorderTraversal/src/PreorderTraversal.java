import java.util.ArrayList;

/**
 * Created by RISKYHE on 2014/5/6.
 */
public class PreorderTraversal {

    public  static void  main(String[] args){

        TreeNode first = new TreeNode(1);
        TreeNode second = new TreeNode(2);
        TreeNode third= new TreeNode(3);

        first.right = second;
        second.left = third;

        Solution sl = new Solution();

        ArrayList<Integer> al = sl.preorderTraversal(first);

        for(Integer in:al){

            System.out.println(in.toString());
        }


    }



    public static class Solution {
        public ArrayList<Integer> preorderTraversal(TreeNode root) {

            ArrayList<Integer> result = new ArrayList<Integer>();

            preRetrive(root, result);

            return result;
        }

        private void preRetrive(TreeNode root, ArrayList<Integer> al) {

            if (root == null){
                return;
            }

            al.add(root.val);
            preRetrive(root.left, al);
            preRetrive(root.right, al);

        }
    }



    public static class TreeNode {
             int val;
             TreeNode left;
             TreeNode right;
             TreeNode(int x) { val = x; }
         }
}
