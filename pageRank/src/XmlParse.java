

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

	        Text page = new Text(pageTitle.replace(" ","+++"));

	        Matcher matcher = pageLinksPattern.matcher(titleText[1]);
	       
	        

	        
	        while(matcher.find()){
	            String linkPage = matcher.group();

	            linkPage = getPageForm(linkPage);

	            if(linkPage == null || linkPage.isEmpty())
	                continue;
	            bw.write(page +"\t" +linkPage+" |  ");
	        }
	        
	        bw.newLine();
	        bw.flush();
	    }

	    private static String getPageForm(String link) {

            if (isNotLinkPage(link))
                return  null;
	        int start = link.startsWith("[[")?2:1;
	        int end = link.indexOf("]");

	        link = link.substring(start,end);
	        link = link.replaceAll("\\s","++++");
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


    private static boolean isNotLinkPage(String link) {


        int start = 1;
        if(link.startsWith("[[")){
            start = 2;
        }


        if (link.length()<start+2 || link.length() > 100)
            return  true;

        char begin = link.charAt(start);


        if (begin == '#') return true;
        if (begin == '.') return true;
        if (begin == '\'') return true;
        if (begin == '{') return true;
        if (begin == '_') return true;

        if (link.contains(":")) return true;
       // if (link.contains(",")) return true;
        if (link.contains("&")) return true;

        return false;
    }



}
