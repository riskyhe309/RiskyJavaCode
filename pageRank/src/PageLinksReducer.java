
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by RISKYHE on 2014/5/1.
 */
public class PageLinksReducer extends Reducer<Text, Text, Text, Text> {

    public void reduce (Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        String pageRank = "1.0\t";

        boolean isFirst = true;

        for (Text value:values){
            if(!isFirst)
                pageRank +=",";

            pageRank += value.toString();
            isFirst = false;
        }

        context.write(key, new Text(pageRank));
    }
}
