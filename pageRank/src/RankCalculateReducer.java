
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


/**
 * Created by RISKYHE on 2014/5/2.
 */
public class RankCalculateReducer extends Reducer<Text, Text, Text, Text> {

    private static final float damping = 0.85F;

    public void reduce (Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        boolean isExitingPage = false;
        String[] split;
        float sumShareLinkRanks = 0;
        String links ="";
        String pageWithRank;


        for (Text value: values){
            pageWithRank = value.toString();

            if(pageWithRank.equals("!")){
                isExitingPage = true;
                continue;
            }

            if (pageWithRank.startsWith("|")){
                links = "\t"+pageWithRank.substring(1);   //delete "|",make it keep the same format like the map's input
                continue;
            }

            //the rest is like "Page_B 1.0 2"
            split = pageWithRank.split("\\t");

            float pageRank = Float.valueOf(split[1]);
            int linkNum = Integer.valueOf(split[2]);

            sumShareLinkRanks += (pageRank/linkNum);
        }


        if (!isExitingPage)
            return;

        float newRank = damping * sumShareLinkRanks +(1-damping);

        context.write(key, new Text(newRank + links));          //the format of  the map's input format

    }
}
