package mytest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class GenerateData {

	public static void main(String[] args) throws Exception {

		BufferedWriter wrs = new BufferedWriter(new FileWriter(new File("C:/Users/Risky/Desktop/data/predict.txt")));

		double[] w = {0.5,0.5,0.5,0.5,0.5,0.5,0.5};
		
		double b = 3.5;
		int index = 0;
		while (index < 280) {
			
			double[] temp = new double[w.length];
			int label = -1;
			double sum = 0;
			for (int i = 0; i < w.length; i++) {
				temp[i] = (float)Math.random()*10;
				sum = w[i] * temp[i];
			}

			if (sum > b)
				label = 1;

			wrs.append(index + " " + label + " "+sum+" ");

			StringBuilder sb = new StringBuilder();
			sb.setLength(0);
			for (int j = 0; j < temp.length; j++) {
				sb.append(temp[j] + " ");

			}
			wrs.append(sb.toString());
			wrs.newLine();
			index++;
		}

		wrs.close();

	}

}
