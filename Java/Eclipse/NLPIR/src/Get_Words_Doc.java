
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Get_Words_Doc {
	public static void main(String[] args) throws IOException {

		String input = "C:/Users/Risky/Desktop/1.txt";
		BufferedReader br = new BufferedReader(new FileReader(new File(input)));
		String line = null;

		String output = "C:/Users/Risky/Desktop/2.txt";
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(output)));

		while ((line = br.readLine()) != null) {

			line = Split_Data_NLPIR.split(line);
			bw.write(line);
			bw.newLine();
		}

		bw.close();
		br.close();
	}
}
