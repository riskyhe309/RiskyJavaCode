package pageRank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.Text;

public class XmlParse {

	/**
	 * Created by RISKYHE on 2014/5/1.
	 * @throws IOException 
	 */
	
	
	 private static final Pattern pageLinksPattern = Pattern.compile("\\[.+?\\]");
	 

	 
	public static void main(String[] args) throws IOException{
		File file = new File ("F:/JavaEclipse/myPageRank/src/pageRank/EXP4_sample_data.txt");
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		
		 File out = new File ("F:/JavaEclipse/myPageRank/src/pageRank/result.txt");
		 
		 BufferedWriter bw = new BufferedWriter(new FileWriter(out));
		 
		 
		String line;
		while((line = br.readLine())!=null){
			Text value = new Text(line);
			getParsing(value,bw);
		}
		
		bw.close();
		br.close();
	}

	  

	    public static void getParsing(Text value, BufferedWriter bw ) throws IOException {

	        String[] titleText = paraeTitleText(value);

	        String pageTitle = titleText[0];

	        Text page = new Text(pageTitle.replace(' ','_'));

	        Matcher matcher = pageLinksPattern.matcher(titleText[1]);
	       
	        
	        String stop = "http://";
	        
	        while(matcher.find()){
	            String linkPage = matcher.group();
	            if (linkPage.contains(stop))
	            	continue;
	            linkPage = getPageForm(linkPage);

	            if(linkPage == null || linkPage.isEmpty())
	                continue;
	            bw.write(page +"%%%%%%%%%" +linkPage+" |  ");
	        }
	        
	        bw.newLine();
	        bw.flush();
	    }

	    private static String getPageForm(String link) {

	        int start = link.startsWith("[[")?2:1;
	        int end = link.indexOf("]");

	        link = link.substring(start,end);
	        link = link.replaceAll("\\s","_");
	        link = link.replaceAll(",","");

	     // is the format  uniform?

	        return link;
	    }


	    private static String[] paraeTitleText(Text value) throws IOException{

	        String[] result = new String[2];

	        //find title
	        int start = value.find("&lttitle&gt");
	        int end = value.find("&lt/title&gt", start);

	        start += 11 ; //add the length of &lttitle&gt

	        result[0] = Text.decode(value.getBytes(),
	                start,end-start);

	        // find links
	        start = value.find("&lttext");
	        start = value.find("&gt",start);
	        end = value.find("&lt/text&gt",start);
	        start +=1;


	        if(start == -1 || end ==-1){
	            return new String[]{"",""};
	        }

	        result[1] = Text.decode(value.getBytes(),
	                start,end-start);


	        return result;
	    }





}
