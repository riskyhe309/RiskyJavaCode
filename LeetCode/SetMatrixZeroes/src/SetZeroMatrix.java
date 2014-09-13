import java.util.Random;

/**
 * Created by RISKYHE on 2014/5/5.
 */


// use the row and column of first zero ,store the info.
// because the row and column will be zero in the end
public class SetZeroMatrix {

    public static void main(String[] arge) {


        Solution sl = new Solution();

        int[][] A = new int[7][9];


        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {

                A[i][j]= (int) (Math.random()*10);
            }
        }

        System.out.println("The origin array");
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {

                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }

        sl.setZeroes(A);

        System.out.println("The change array");

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {

                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }


    }


    public static class Solution {

        public void setZeroes(int[][] matrix) {

            int row = matrix.length;
            int column = matrix[0].length;

            if (row * column == 0) {
                return;
            }
            //mark the row and column of first zero
            int tempRow = -1;
            int tempColumn = 0;

            boolean isZero = false;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    if (matrix[i][j] == 0) {
                        tempRow = i;
                        tempColumn = j;
                        isZero = true;
                        break;
                    }
                }

                if (isZero)
                    break;
            }

            if (tempRow < 0){
                return;
            }

            // use the row and column of first zero ,store the info

            for (int i = tempRow; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    if (matrix[i][j] == 0) {

                        matrix[tempRow][j] = 0;    //the entire jth column is zero
                        matrix[i][tempColumn] = 0; //the entire ith row is zero
                    }

                }
            }


            //retrive the row of tempRow
            for (int i = 0; i < column; i++) {

                if (matrix[tempRow][i] == 0 && i != tempColumn) {
                    setColumnZero(i, matrix);
                }


                //set the tempRow,coulmn zero ,and next column
                if (matrix[tempRow][i] != 0) {
                    matrix[tempRow][i] = 0;
                }

            }


            //retrive the column of tempRow
            for (int j = 0; j < row; j++) {

                if (matrix[j][tempColumn] == 0 && j != tempRow) {
                    setRowZero(j, matrix);
                }


                if (matrix[j][tempColumn] != 0) {
                    matrix[j][tempColumn] = 0;
                }
            }


        }

        private void setRowZero(int zeroRow, int[][] matrix) {
            for (int i = 0; i < matrix[0].length; i++) {
                matrix[zeroRow][i] = 0;
            }
        }

        private void setColumnZero(int zeroColumn, int[][] matrix) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][zeroColumn] = 0;
            }
        }
    }


}