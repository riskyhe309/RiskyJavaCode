package pageRank;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by RISKYHE on 2014/5/1.
 */
public class PageLinksMapper extends Mapper<LongWritable, Text, Text, Text> {

    private static final Pattern pageLinksPattern = Pattern.compile("\\[.+?\\]");

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] titleText = paraeTitleText(value);

        String pageTitle = titleText[0];

        Text page = new Text(pageTitle.replace(' ','_'));

        Matcher matcher = pageLinksPattern.matcher(titleText[1]);

        String stop = "http://";
        
        while(matcher.find()){
            String linkPage = matcher.group();
            linkPage = getPageForm(linkPage);
            
            //drop the out links
            if (linkPage.contains(stop))
            	continue;

            if(linkPage == null || linkPage.isEmpty())
                continue;


            context.write(page, new Text(linkPage));
        }

    }

    private String getPageForm(String link) {

        int start = link.startsWith("[[")?2:1;
        int end = link.indexOf("]");

        link = link.substring(start,end);
        link = link.replaceAll("\\s","_");
        link = link.replaceAll(",","");

     // is the format  uniform?

        return link;
    }


    private String[] paraeTitleText(Text value) throws CharacterCodingException {

        String[] result = new String[2];

        //find title
        int start = value.find("&lttitle&gt");
        int end = value.find("&lt/title&gt", start);

        start += 11 ; //add the length of <title>

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

