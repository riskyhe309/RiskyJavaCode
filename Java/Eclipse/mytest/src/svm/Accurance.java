package svm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class Accurance {

	public static void main(String[] args) throws Exception {

		String path1 = "D:\\part-r-00000";
		String path2 = "D:\\STDpendigits.t";

		FileInputStream fis1 = new FileInputStream(new File(path1));
		FileInputStream fis2 = new FileInputStream(new File(path2));

		BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1));
		BufferedReader br2 = new BufferedReader(new InputStreamReader(fis2));

		String line1 = null;
		String line2 = null;

		double total = 0;
		int error = 0;
		int accurance = 0;
		while ((line1 = br1.readLine()) != null && (line2 = br2.readLine()) != null) {

			total++;
			double tokens1 = Double.parseDouble((line1.split("\\s+"))[1]);
			double tokens2 = Double.parseDouble((line2.split("\\s+"))[1]);

			if (tokens1 != tokens2)
				error++;
			else
				accurance++;

		}

		System.out.println("The error rate is :" + error / total);
		System.out.println("The accuracy rate is :" + accurance / total);
		System.out.println("The total  number is :" + total);
		System.out.println("The error  number is :" + error);
		System.out.println("The accurance  number is :" + accurance);
				

	}
}
