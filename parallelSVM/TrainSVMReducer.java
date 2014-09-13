package parallelSVM;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TrainSVMReducer extends Reducer<LongWritable, Text, Text, Text> {

	//long supportVectorNum = 0;

	public void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException,
			InterruptedException {

		for (Text value : values) {

			String[] line = value.toString().split("\\s+");

			String thisKey = line[0];
			StringBuilder sb = new StringBuilder();

			sb.setLength(0);
			
			for (int i = 0; i < line.length; i++) {
				
				sb.append(line[i]);
				
				if (i != line.length - 1)
					
					sb.append(" ");

			}			
			context.write(new Text(thisKey), new Text(sb.toString()));
		}
	}

	
//	protected void cleanup(Context context) {
//
//		Configuration conf = context.getConfiguration();
//		long prev = conf.getLong("support_vector_num", 0);
//		conf.setLong("support_vector_num", supportVectorNum + prev);
//	}
}
