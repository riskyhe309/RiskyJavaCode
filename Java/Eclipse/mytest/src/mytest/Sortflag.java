package mytest;

public class Sortflag {

	static String[] array = "0".split(",");

	public static void main(String[] args) {

		int flag0 = 0;
		int flag1 = 0;
		int flag2 = array.length - 1;

		while (flag1 <= flag2) {

			if (array[flag1].equals("0")) {
				swap(flag1, flag0);
				flag0++;
				flag1++;
			}

			else if (array[flag1].equals("1"))
				flag1++;
			else {
				
				while (flag1 <= flag2 && array[flag2].equals("2"))
					flag2--;

				if (flag1 < flag2) {
					swap(flag1, flag2);
					flag2--;

				}

			}

		}

		for (String s : array) {
			System.out.print(s+" ");
		}

	}

	private static void swap(int rflag, int wflag) {

		String temp;
		temp = array[rflag];
		array[rflag] = array[wflag];
		array[wflag] = temp;
	}
}
