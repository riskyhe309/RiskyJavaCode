package pageRank;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MyPR extends Configured implements Tool {

	private static NumberFormat nf = new DecimalFormat("00");

	public static void main(String[] args) throws Exception {

		System.exit(ToolRunner.run(new Configuration(), new MyPR(), args));

	}

	private boolean pageParsing(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();

		Job job = new Job(conf, "PageParsing");
		job.setJarByClass(MyPR.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.setInputPaths(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));

		job.setMapperClass(PageLinksMapper.class);
		job.setReducerClass(PageLinksReducer.class);

		return job.waitForCompletion(true);

	}

	private boolean rankCalculate(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException {

		Configuration conf = new Configuration();

		Job job = new Job(conf, "RankCalculator");
		job.setJarByClass(MyPR.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.setInputPaths(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));

		job.setMapperClass(RankCalculateMapper.class);
		job.setReducerClass(RankCalculateReducer.class);

		return job.waitForCompletion(true);
	}

	private boolean pageOrder(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();

		Job job = new Job(conf, "RankOrder");
		job.setJarByClass(MyPR.class);

		job.setOutputKeyClass(FloatWritable.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.setInputPaths(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));

		job.setMapperClass(RankingMapper.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		return job.waitForCompletion(true);
	}

	@Override
	public int run(String[] arg) throws Exception {

		boolean isComplete = pageParsing("/data/enwiki",
				"out/wiki/ranking/iter00");

		if (!isComplete)
			return 1;

		// Job2 Calculate new rank
		int cycle = 0;
		for (; cycle < 1; cycle++) {

			isComplete = rankCalculate(
					"out/wiki/ranking/iter" + nf.format(cycle),
					"out/wiki/ranking/iter" + nf.format(cycle + 1));
		}

		if (!isComplete)
			return 1;
		
		// Job3 Order the rank
		isComplete = pageOrder("out/wiki/ranking/iter" + nf.format(cycle),
				"out/wiki/result");

		if (!isComplete)
			return 1;
		
		return 0;
	}

}
