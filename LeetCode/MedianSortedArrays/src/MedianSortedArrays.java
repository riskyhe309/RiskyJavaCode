/**
 * Created by RISKYHE on 2014/5/8.
 */
public class MedianSortedArrays {

    public static void main(String[] args) {

        Solution sl = new Solution();

        // int[][] a = {{1, 3, 5,7}, {10, 11, 16,20}, {23, 30, 34,50}};

        //int[][] a = {{1},{3}};

        int[] a = {0, 3, 5, 6, 7, 8};
        int[] b = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        System.out.println(sl.findMedianSortedArrays(a, b));

    }


    public static class Solution {
        public double findMedianSortedArrays(int A[], int B[]) {

            int i = A.length;
            int j = B.length;
            int length = i + j;

            if ((length & 0x1) == 1) {
                return searchKthElement(A, i, B, j, length / 2 + 1);
            } else {
                return (searchKthElement(A, i, B, j, length / 2)
                        + searchKthElement(A, i, B, j, length / 2 + 1)) / 2;
            }


        }

        private double searchKthElement
                (int[] a, int lengtha, int[] b, int lengthb, int k) {


            // make sure a is smaller than b
            if (lengtha > lengthb) {
                return searchKthElement(b, lengthb, a, lengtha, k);
            }

            if (lengtha == 0){
                return b[k - 1];
            }

            if (k == 1){
                return Math.min(a[0],b[0]);
            }
            int ka= Math.min(k / 2, lengtha);
            int kb = k -ka;

            if (a[ka-1] < b[kb-1]){
               a = getArray(a,ka);
                return searchKthElement(a, lengtha - ka, b, lengthb, k - ka);
            }
            else if (a[ka-1] > b[kb-1]){
                b = getArray(b,kb);
                return searchKthElement(a,lengtha,b,lengthb - kb,k-kb);
            }
            else return a[ka-1];

        }

        private int[] getArray(int[] a, int index) {

            int[] result = new int[a.length - index];

            for(int i = 0;i<result.length;i++ ){
                result[i] = a[i+index];
            }
            return result;
        }
    }
}