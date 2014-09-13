package parallelSVM;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Driver {

	//static int cycle = 0;

	/*
	 * main function
	 */
	public static void main(String[] args) throws Exception {

		if (train("out/SVM/train"))
			predict("out/SVM/predict", "out/final/result");

	}

	/*
	 * train function to the train map
	 */
	private static boolean train(String inputPath) throws Exception {

		String input = inputPath;
		String output = null;

		// long supportVectorNum = Long.MAX_VALUE; // the SVNum
		// long pre = 0; // the preiter SVNum
		
		boolean flag = false;
		int iter = 0;

		for (iter = 0; iter < 2; iter++) {
			
						
			output = "out/result/iter" + iter;

			// pre = supportVectorNum;

			flag = getSupportVector(input, output);

			input = "out/result/iter" + iter;

		}

		return flag;

	}

	/*
	 * the train job's configuration
	 */
	private static boolean getSupportVector(String inputPath, String outputPath) throws IOException, Exception,
			InterruptedException {

		Configuration conf = new Configuration();

		Job job1 = new Job(conf, "TrainSVs");

		conf.setLong("support_vector_num", 0);

		job1.setJarByClass(Driver.class);

		job1.setMapOutputKeyClass(LongWritable.class);
		job1.setMapOutputValueClass(Text.class);

		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job1, new Path(inputPath));
		FileOutputFormat.setOutputPath(job1, new Path(outputPath));

		job1.setMapperClass(TrainSVMMapper.class);
		job1.setReducerClass(TrainSVMReducer.class);

		conf = job1.getConfiguration();

		//long supportVectorNum = conf.getLong("support_vector_num", 0);

		job1.waitForCompletion(true);

		return job1.waitForCompletion(true);
	}

	/*
	 * predict the testdata
	 */
	private static boolean predict(String inputPath, String outputPath) throws IOException, ClassNotFoundException,
			InterruptedException, URISyntaxException {

		Configuration conf = new Configuration();

		DistributedCache.addCacheFile(new URI("hdfs://master01:54310/user/2014st08/out/result/iter1/part-r-00000"), conf);

		Job job2 = new Job(conf, "Predict");
		job2.setJarByClass(Driver.class);

		job2.setOutputKeyClass(LongWritable.class);
		job2.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job2, new Path(inputPath));
		FileOutputFormat.setOutputPath(job2, new Path(outputPath));

		job2.setMapperClass(PredictSVMMapper.class);

		job2.setMapOutputKeyClass(LongWritable.class);
		job2.setMapOutputValueClass(Text.class);

		return job2.waitForCompletion(true);

	}

}
