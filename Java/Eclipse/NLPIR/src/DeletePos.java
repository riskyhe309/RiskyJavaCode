import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class DeletePos {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		 File wordsWith = new File("C:/Users/Risky/Downloads/dataset_602156/words_NLPIR.txt");
		 
		 File wordsNonPos = new File("C:/Users/Risky/Downloads/dataset_602156/words_NLPIRNoPos.txt");
		 
		 
		 BufferedReader br = new BufferedReader(new FileReader(wordsWith));

         BufferedWriter bw = new BufferedWriter(new FileWriter(wordsNonPos));

         String line;
         while (true){
             if ((line = br.readLine()) == null) break;

             String[] tempLine = line.split("\\s+");

             for (String s: tempLine){
                 String[] tempStr = s.split("/");
                 if (tempStr[0].matches("[^\u4e00-\u9fa5]+") || tempStr[0].startsWith("@"))
                     continue;
                 bw.write(tempStr[0]+" ");
             }

             bw.newLine();

         }
         bw.flush();
         bw.close();
         

	}

}
