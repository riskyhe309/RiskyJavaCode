package parallelSVM;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PredictSVMMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

	private Path[] localFiles;
	private List<List<Double>> trainData = new ArrayList<List<Double>>();
	private List<Double> yLabels = new ArrayList<Double>(); // the labels
	private svm_model svmModel;

	private double[] y; // labels of train data
	private svm_node[][] x; // features of tainData

	/*
	 * get the supportVector
	 */
	public void setup(Context context) throws IOException {

		Configuration conf = context.getConfiguration();
		localFiles = DistributedCache.getLocalCacheFiles(conf); // get the
																// supportVectors

		for (int i = 0; i < localFiles.length; i++) {

			String line = null;

			BufferedReader br = new BufferedReader(new FileReader(localFiles[i].toString()));

			while ((line = br.readLine()) != null) {

				String[] tokens = line.split("\\s+");

				yLabels.add(Double.valueOf(Double.parseDouble(tokens[1])));

				List<Double> data = new ArrayList<Double>();

				for (int j = 2; j < tokens.length; j++) {

					data.add(Double.parseDouble(tokens[j]));
				}
				
				trainData.add(data);
			}

			br.close();
		}

		convertData();

		svmModel = trainModel(x, y); // get the model

	}

	/*
	 * map function
	 */
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		String line = value.toString();
		String[] tokens = line.split("\\s+");
		svm_node[] features = new svm_node[tokens.length - 2];
		StringBuilder sb = new StringBuilder();
		sb.setLength(0);
		for (int i = 0; i < features.length; i++) {
			svm_node node = new svm_node();
			node.index = i;
			node.value = Double.parseDouble(tokens[i + 2]);
			features[i] = node;
			sb.append(" " + node.value);
		}

		int label = (int) svm.svm_predict(svmModel, features);

		int index = Integer.parseInt(tokens[0]);

		sb.insert(0, label);

		context.write(new LongWritable(index), new Text(sb.toString()));

	}

	/*
	 * convert the List to array
	 */
	private void convertData() {

		Double[] yTemp = yLabels.toArray(new Double[yLabels.size()]);

		y = new double[yLabels.size()];

		for (int i = 0; i < yTemp.length; ++i) {
			y[i] = yTemp[i];
		}

		x = new svm_node[trainData.size()][];

		for (int i = 0; i < trainData.size(); ++i) {
			svm_node[] temp = new svm_node[trainData.get(i).size()];
			for (int j = 0; j < trainData.get(i).size(); j++) {
				svm_node node = new svm_node();
				node.index = j;
				node.value = trainData.get(i).get(j);
				temp[j] = node;
			}
			x[i] = temp;
		}
	}

	/*
	 * train the model
	 */
	private svm_model trainModel(svm_node[][] data, double[] trainLabels) {

		// define the svm problem
		svm_problem problem = new svm_problem();

		problem.l = data.length; // the vectors num
		problem.x = data;
		problem.y = trainLabels;

		// define the svm param
		svm_parameter param = new svm_parameter();
		param.svm_type = svm_parameter.C_SVC;
		param.kernel_type = svm_parameter.RBF;
		param.cache_size = 200;
		param.eps = 1e-3;
		param.C = 30;
		param.gamma = 0.5;

		svm.svm_check_parameter(problem, param);

		if (problem.x == null || problem.y == null) {
			return null;
		}
		svm_model model = svm.svm_train(problem, param);
		return model;
	}

}
