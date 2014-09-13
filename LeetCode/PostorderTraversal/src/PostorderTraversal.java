import java.util.ArrayList;

/**
 * Created by RISKYHE on 2014/5/6.
 */
public class PostorderTraversal {


    public  static void  main(String[] args){

        TreeNode first = new TreeNode(1);
        TreeNode second = new TreeNode(2);
        TreeNode third= new TreeNode(3);

        first.right = second;
        second.left = third;

        Solution sl = new Solution();

       ArrayList<Integer> al = sl.postorderTraversal(first);

        for(Integer in:al){

            System.out.println(in.toString());
        }


    }


    public static class Solution {
        public ArrayList<Integer> postorderTraversal(TreeNode root) {

            ArrayList<Integer> result = new ArrayList<Integer>();

            postRetrive(root,result);

            return result;
        }

        private void postRetrive(TreeNode root, ArrayList<Integer> al) {
            if (root == null){
                return;
            }

            postRetrive(root.left, al);
            postRetrive(root.right, al);
            al.add(root.val);
        }
    }


    public static class TreeNode {
             int val;
             TreeNode left;
             TreeNode right;
             TreeNode(int x) { val = x; }
         }

}
