import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Test {

	public static void main(String[] args) throws IOException {

		String input = "C:/Users/Risky/Desktop/1.txt";;
		
		BufferedReader br = new BufferedReader(new FileReader(new File(input)));
		
		Set<String> candidateSet = new HashSet<String>();
		String line;

		while ((line = br.readLine()) != null) {
			String[] strings = line.split("\\s+");
			if (!strings[0].equals(" "))
				Split_Data_NLPIR.split(strings[0]);

		}
		br.close();

	}
}
