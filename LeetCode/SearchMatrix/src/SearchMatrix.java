/**
 * Created by RISKYHE on 2014/5/7.
 */
public class SearchMatrix {


    public static void main(String[] args) {

        Solution sl = new Solution();

       // int[][] a = {{1, 3, 5,7}, {10, 11, 16,20}, {23, 30, 34,50}};

        //int[][] a = {{1},{3}};

        int[][] a = {{0}};

        System.out.println(sl.searchMatrix(a, 0));

    }


    public static class Solution {
        public boolean searchMatrix(int[][] matrix, int target) {

            if (matrix.length == 0) {
                return false;
            }

            int row = binarySearchRow(matrix, 0, matrix.length - 1, target);


            if (row < 0) {
                return false;
            }


            return binarySearchColumn(matrix[row], 0, matrix[0].length - 1, target);
        }

        private boolean binarySearchColumn(int[] row, int start, int end, int target) {


            if (start > end) {
                return false;
            }
            // boolean find = false;
            int middle = (start + end) / 2;
            if (row[middle] > target) {

                return binarySearchColumn(row, start, middle - 1, target);
            } else if (row[middle] < target) {
                return binarySearchColumn(row, middle + 1, end, target);
            }

            return true;
        }


        private int binarySearchRow(int[][] matrix, int start, int end, int target) {


//            if (matrix[end][0] < target ) {
//                return end;
//            }
//
//            if (matrix[start][0] > target ) {
//                return -1;
//            }

            if (start > end) {
//                if (matrix[start][0] > target) {
//                    return start - 1;
//                } else {
//                    return start;
//                }

                return  (start + end) / 2;

            }

            int middle = (start + end) / 2;

            if (matrix[middle][0] > target) {

                return binarySearchRow(matrix, start, middle - 1, target);
            } else if (matrix[middle][0] < target) {

                return binarySearchRow(matrix, middle + 1, end, target);
            }

            return middle;

        }


    }
}
