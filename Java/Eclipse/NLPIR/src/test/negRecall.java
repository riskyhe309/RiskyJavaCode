package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class negRecall {

	public static void main(String[] args) throws IOException {

		Set<String> setCand = new HashSet<>();
		Set<String> setDic = new HashSet<>();
		String input = "C:\\Users\\Risky\\Desktop\\1.txt";
		BufferedReader br = new BufferedReader(new FileReader(new File(input)));
		String line = null;
		while((line = br.readLine())!=null){
			String[] temp = line.split("\\s");
			setCand.add(temp[0]);
		}
		br.close();
		
		
		String input1 = "C:/Users/Risky/Downloads/senmitic/ntusd-negative.txt";
		BufferedReader br1 = new BufferedReader(new FileReader(new File(input1)));
		
		while((line = br1.readLine())!=null){
			String[] temp = line.split("\\s");
			setDic.add(temp[0]);
		}
		br1.close();
		
		int count = 0;
		for(String s:setCand){
			if(setDic.contains(s))
				count++;
		}
		
		System.out.println(count);
	}
}
