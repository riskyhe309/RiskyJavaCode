
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.nio.charset.CharacterCodingException;

/**
 * Created by RISKYHE on 2014/5/2.
 */
public class RankingMapper extends Mapper<LongWritable, Text, FloatWritable, Text> {

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] pageAndRank = getPageAndRank(key, value);

        float parseFloat = Float.parseFloat(pageAndRank[1]);

        Text page = new Text(pageAndRank[0]);
        FloatWritable rank = new FloatWritable(parseFloat);

        context.write(rank, page);
    }

    private String[] getPageAndRank(LongWritable key, Text value) throws CharacterCodingException {

        String[] result = new String[2];

        int tabPageIndex = value.find("\t");
        int tabRankIndex = value.find("\t", tabPageIndex + 1);

        //
        int length;

        if (tabRankIndex == -1) {   //no table after rank (the page has no links)
            length = value.getLength() - (tabPageIndex + 1);
        } else {
            length = tabRankIndex - (tabPageIndex + 1);
        }

        result[0] = Text.decode(value.getBytes(),
                0, tabPageIndex);

        result[1] = Text.decode(value.getBytes(),
                tabPageIndex + 1, length);
        return result;

    }
}
