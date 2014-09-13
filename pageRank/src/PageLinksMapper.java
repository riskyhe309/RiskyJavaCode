
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

        Text page = new Text(pageTitle.replace(' ', '_'));

        Matcher matcher = pageLinksPattern.matcher(titleText[1]);


        while (matcher.find()) {
            String linkPage = matcher.group();

            linkPage = getPageForm(linkPage);

            if (linkPage == null || linkPage.isEmpty())
                continue;


            context.write(page, new Text(linkPage));
        }

    }

    private String getPageForm(String link) {

        if (isNotLinkPage(link))
            return null;

        int start = link.startsWith("[[") ? 2 : 1;
        int end = link.indexOf("]");

        //some links like [[realPage | linkName]]
        int linkWithName = link.indexOf('|');
        if (linkWithName > 0) {
            end = linkWithName;
        }

        //some links to files or paragraphs
        int part = link.indexOf('#');
        if (part > 0) {
            end = part;
        }


        link = link.substring(start, end);
        link = link.replaceAll("\\s", "_");
        link = link.replaceAll(",", "");


        return link;
    }


    //If the ling is not a wiki pageLink
    private boolean isNotLinkPage(String link) {


        int start = 1;
        if (link.startsWith("[[")) {
            start = 2;
        }


        if (link.length() < start + 2 || link.length() > 100)
            return true;

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


    private String[] paraeTitleText(Text value) throws CharacterCodingException {

        String[] result = new String[2];

        //find title
        int start = value.find("&lttitle&gt");
        int end = value.find("&lt/title&gt", start);

        start += 11; //add the length of <title>

        result[0] = Text.decode(value.getBytes(),
                start, end - start);

        // find links
        start = value.find("&lttext");
        start = value.find("&gt", start);
        end = value.find("&lt/text&gt", start);
        start += 1;


        if (start == -1 || end == -1) {
            return new String[]{"", ""};
        }

        result[1] = Text.decode(value.getBytes(),
                start, end - start);


        return result;
    }


}

