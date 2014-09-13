package pageRank;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;



/**
 * Created by RISKYHE on 2014/5/2.
 */
public class RankCalculateMapper extends Mapper<LongWritable, Text, Text ,Text> {

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        int pageTabIndex = value.find("\t");
        int rankTabIndex = value.find("\t",pageTabIndex+1);

        String page = Text.decode(value.getBytes(),
                0,pageTabIndex);

        String pageWithRank = Text.decode(value.getBytes(),
                0,rankTabIndex+1); //include the space

        // Mark the page is an exiting page
        context.write(new Text(page), new Text("!"));

        // Skip the page without links
        if (rankTabIndex == -1)
            return;

        String links = Text.decode(value.getBytes(),
                rankTabIndex+1,value.getLength()-(rankTabIndex+1));
        String[] allLinks = links.split(",");

        int linksNum = allLinks.length;

        for(String link : allLinks){
            Text rankAndLinks = new Text(pageWithRank + linksNum);
            context.write(new Text(link), rankAndLinks);
        }

        context.write(new Text(page), new Text("|"+links));
    }
}
