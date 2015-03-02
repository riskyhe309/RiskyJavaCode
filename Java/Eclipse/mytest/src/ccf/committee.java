package ccf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class committee {

	public static void main(String[] args) throws IOException {

		String path1 = "C:\\Users\\Risky\\Desktop\\Book2.csv";
		String path2 = "D:\\tabel.txt";

		FileInputStream fis = new FileInputStream(new File(path1));

		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path2)));

		String line = null;

		int index = 0;
		br.readLine();
		while ((line = br.readLine()) != null) {
			if (index == 0){
				bw.write("<tr>");
				bw.newLine();
			}
				

			String[] str = line.split(",");
			//String[] temp = str[0].split("\\s+");
			
			String name = str[0];
			
			if (name.length() <3)
				name = name.substring(0, 1) + "&nbsp;&nbsp;&nbsp;" + name.substring(1, 2) ;

			bw.write("<td><li>&nbsp;&nbsp;" + name + "£¨" + str[1] + "£©" + "</li></td>");
			bw.newLine();
			bw.flush();

			index++;

			if (index == 3) {
				index = 0;
				bw.write("</tr>");
				bw.newLine();
				bw.newLine();
			}
		}

		if (index != 0){
			bw.write("</tr>");
			bw.newLine();
		}
		

		br.close();
		bw.close();
	}

}
