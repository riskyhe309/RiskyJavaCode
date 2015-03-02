package mytest;

import java.io.*;

public class DataSet {

	private static double[][] features;
	private static double[] labels;
	private static int numAttributes;
	private static int numInstnaces;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		BufferedReader reader = new BufferedReader(new FileReader("E:/data/segment.data"));
		String[] attInfo = reader.readLine().split(","); // attributes info
		numAttributes = attInfo.length - 1;

		numInstnaces = 0;
		while (reader.readLine() != null) {
			numInstnaces++;
		}

		features = new double[numInstnaces][numAttributes];
		labels = new double[numInstnaces];
		System.out.println("reading " + numInstnaces + " exmaples with " + numAttributes + " attributes");

		reader = new BufferedReader(new FileReader("E:/data/segment.data"));
		reader.readLine();
		String line;
		int ind = 0; 
		while ((line = reader.readLine()) != null) {
			String[] atts = line.split(",");
			for (int i = 0; i < atts.length - 1; i++) {
				features[ind][i] = Double.parseDouble(atts[i]);
			}
			labels[ind] = Double.parseDouble(atts[atts.length - 1]) > 4 ? 1 : -1;
			ind++;
		}
		reader.close();

		BufferedWriter wrs = new BufferedWriter(new FileWriter(new File("C:/Users/Risky/Desktop/data/train.txt")));

		for (int i = 0; i < labels.length; i++) {

			wrs.append(i + " " + labels[i]+" ");
			StringBuilder sb = new StringBuilder();
			sb.setLength(0);
			for (int j=0;j<features[i].length;j++){
				sb.append(features[i][j] + " ");
				
			}
			wrs.append(sb.toString());
			wrs.newLine();
		}
		wrs.close();

	}

}
