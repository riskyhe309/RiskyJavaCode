package mytest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Read {

	public static void main(String[] args) throws IOException {
		String path = "C:/Users/Risky/Downloads/SVM/JSvmLib/src/lib/SimplifiedSMO/heart_scale.txt";

		FileInputStream fis = new FileInputStream(new File(path));

		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		BufferedWriter wr = new BufferedWriter(
				new FileWriter(
						new File(
								"C:/Users/Risky/Downloads/SVM/JSvmLib/src/lib/SimplifiedSMO/heart.txt")));
		String line = null;
		int index = 0;
		while ((line = br.readLine()) != null) {
			
			wr.append(index+++" "); 
			
			String[] tem = line.split("\\s+|:");
			
			for(int i=0;i<tem.length;i++){
				wr.append(tem[i]+" ");
				i++;
			}
			wr.newLine();
	
			//System.out.println(tem);
		}
		wr.flush();
		wr.close();
		br.close();
		// System.out.println(fis.read());
	}
}
