package mytest;
public class Solution {
	public  void sortColors(int[] A) {

		int flag0 = 0;
		int flag1 = 0;
		int flag2 = A.length - 1;

		while (flag1 <= flag2) {

			if (A[flag1] == 0) {
				swap(flag1, flag0,A);
				flag0++;
				flag1++;
			}

			else if (A[flag1] == 1)
				flag1++;
			else {
				
				while (flag1 <= flag2 && A[flag2] == 2)
					flag2--;

				if (flag1 < flag2) {
					swap(flag1, flag2,A);
					flag2--;

				}

			}

		}

		for (int s : A) {
			System.out.print(s+" ");
		}

	}

	private static void swap(int rflag, int wflag,int[] A) {

		int temp;
		temp = A[rflag];
		A[rflag] = A[wflag];
		A[wflag] = temp;
	}
}
