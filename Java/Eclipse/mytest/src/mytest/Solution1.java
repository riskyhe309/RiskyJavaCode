package mytest;

public class Solution1 {
	public void sortColors(int[] A) {

		int flag0 = 0;
		int flag1 = 0;
		int flag2 = A.length - 1;

		while (flag1 <= flag2) {

			if (A[flag1] == 0) {

				int temp;
				temp = A[flag1];
				A[flag1] = A[flag0];
				A[flag0] = temp;

				flag0++;
				flag1++;
			}

			else if (A[flag1] == 1) {

				flag1++;
			}

			else {

				while (flag1 <= flag2 && A[flag2] == 2)
					flag2--;

				if (flag1 < flag2) {

					int temp;
					temp = A[flag1];
					A[flag1] = A[flag2];
					A[flag2] = temp;

					flag2--;

				}

			}

		}

	}

}
