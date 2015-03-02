

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Statistics_senmiticDic {

	public static void main(String[] args) throws IOException {

		String input = "C:/Users/Risky/Downloads/dataset_602156/words_NLPIRNoPos.txt";
		BufferedReader br = new BufferedReader(new FileReader(new File(input)));
		String line = null;

		String input1 = "C:/Users/Risky/Downloads/senmitic/ntusd-positive.txt";
		BufferedReader br1 = new BufferedReader(new FileReader(new File(input1)));
		String line1 = null;

		Map<String, Integer> mapPosMap = new HashMap<String, Integer>();
		while ((line1 = br1.readLine()) != null) {
			// remove space
			//line1 = line1.replaceAll("\\s*", "");
			mapPosMap.put(line1, 0);
		}
		br1.close();

		// Map<String, List<Integer>> map = new HashMap<String,
		// List<Integer>>();

		String output = "C:/Users/Risky/Downloads/dataset_602156/Statistic_senmiticDic_pos.txt";
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(output)));

		while ((line = br.readLine()) != null) {

			String[] strs = line.split("\\s");

			for (String s : strs) {

				if (mapPosMap.containsKey(s)) {

					Integer valueInteger = mapPosMap.get(s);
					mapPosMap.put(s, valueInteger + 1);
				}

			}

		}
		br.close();
		Set<String> set = mapPosMap.keySet();
		Iterator<String> iterator = set.iterator();

		while (iterator.hasNext()) {
			String tString = iterator.next();
			Integer valueInteger = mapPosMap.get(tString);
			if (valueInteger > 800) {
				bw.write(tString + " " + valueInteger);
				bw.newLine();
			}

		}
		bw.flush();
		bw.close();

	}
}
