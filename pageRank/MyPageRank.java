package pageRank;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MyPageRank {
	
	//mapper 
	public class PageRankMapper extends Mapper<LongWritable, Text, Text,Text>{
		//Map function
		public void map(LongWritable key, Text value, Context context) 
				throws IOException, InterruptedException{
			
			String[] str = value.toString().split("\t");
			
			Text id = new Text(str[0]);
			
			String[] values = str[1].split("\\s");
			
			@SuppressWarnings("unused")
			DoubleWritable intiRank = new DoubleWritable(Double.valueOf(values[0]));
			
			String links = "_";
			
			for(int i = 1; i < values.length; i++){
				
				links += values[i]+" ";
				Text page = new Text(values[i]);
				
				context.write(page, new Text(String.valueOf(Double.valueOf(values[0])
						/(values.length-1))));
				
			}
			
			context.write(id, new Text(links));
			
		}		
	}
	
	
	//reducer
	public class PageRankReducer extends Reducer<Text, Text, Text, Text>{
		//reducer function
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
			
			double sum = 0;
			
			String links = "";
			
			for(Text value:values){
				
				String str = value.toString();
				
				if(!str.contains("_")){   //if not contains "_", this is a rank value
					
					sum += Double.valueOf(str);
				} else{		//this is a link
					
					links = str.replaceAll("_", " ");  // the link begin with "_"
				}
			}
			
			Double pr = 0.85 * sum + 0.15;
			
			context.write(key, new Text(pr+links));
			
		}	
	}
	
	
	
	//main function
	
	public static void main(String[] args) throws IOException, 
	ClassNotFoundException, InterruptedException{
		
		NumberFormat nf = new DecimalFormat("00");
		
		
		for(int i = 0; i < 10; i++){
			
			Path input = new Path("/myRank/out/"+nf.format(i));
			
			Path output = new Path("/myRank/out/"+nf.format(i+1));
			
			
			Configuration conf = new Configuration();
			
			Job job = new Job (conf,"MyPageRank");
			
			job.setJarByClass(MyPageRank.class);
		
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			FileInputFormat.setInputPaths(job, input);
			FileOutputFormat.setOutputPath(job, output);
			
			job.setMapperClass(PageRankMapper.class);
			job.setReducerClass(PageRankReducer.class);
			
			System.exit(job.waitForCompletion(true)?0:1);
		}
		
		
	}

	
}
