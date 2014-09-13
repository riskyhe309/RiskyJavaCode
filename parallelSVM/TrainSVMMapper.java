package parallelSVM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TrainSVMMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

	private List<Double> yLabels = new ArrayList<Double>(); // the labels

	private int globalCount = 0; // the vector number of a map

	private static final int PER_MAPPER_VECTORS = 10000;

	private List<List<Double>> trainData = new ArrayList<List<Double>>();

	private static final int DIV_NUM = 8;

	private double[] y; // labels of train data
	private svm_node[][] x; // features of tainData

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		globalCount++;

		if (globalCount < PER_MAPPER_VECTORS) {

			String line = value.toString();
			String[] tokens = line.split("\\s+");

			yLabels.add(Double.parseDouble(tokens[1]));

			List<Double> data = new ArrayList<Double>();

			for (int i = 2; i < tokens.length; i++) {

				data.add(Double.parseDouble(tokens[i]));
			}

			trainData.add(data);

		} else {
			execute(context);
			yLabels.clear();
			trainData.clear();
			globalCount = 0;
		}

	}

	@Override
	/*
	 * the remains tainData
	 */
	protected void cleanup(Context context) throws IOException, InterruptedException {

		if (globalCount < PER_MAPPER_VECTORS && globalCount > 0) {
			execute(context);
			yLabels.clear();
			trainData.clear();
			globalCount = 0;
		}
	}

	/*
	 * execute the map
	 */
	private void execute(Context context) throws IOException, InterruptedException {

		convertData();

		Random random = new Random();

		int key = random.nextInt(10) % DIV_NUM;

		svm_model model = trainModel(x, y);
		svm_node[][] supportVectors = model.SV;

		// if we cannot find any support vectors, we just out put the whole
		// dataset

		if (model.SV == null || model.SV.length == 0) {

			outputDataSet(key, context, x, y);

		} else {
			// output SupportVectors
			int[] labels = getSupportVectorLabels(supportVectors, model);

			outputSupportVectors(key, context, supportVectors, labels);
		}

	}

	/*
	 * output the SupportVectors
	 */
	private void outputSupportVectors(int key, Context context, svm_node[][] supportVectors, int[] labels)
			throws IOException, InterruptedException {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < supportVectors.length; i++) {

			sb.setLength(0);

			sb.append(labels[i]);
			sb.append(" ");

			for (int j = 0; j < supportVectors[i].length; j++) {

				sb.append(supportVectors[i][j].value);

				if (j != supportVectors[i].length - 1)
					sb.append(" ");
			}
			context.write(new LongWritable(key), new Text(sb.toString()));
		}
	}

	/*
	 * output the dataSet
	 */
	private void outputDataSet(int key, Context context, svm_node[][] x2, double[] y2) throws IOException,
			InterruptedException {
		int[] labels = new int[y2.length];

		for (int i = 0; i < y2.length; i++) {
			labels[i] = (int) y2[i];
		}

		outputSupportVectors(key, context, x2, labels);
	}

	/*
	 * get the labels of the SupportVectors
	 */
	private int[] getSupportVectorLabels(svm_node[][] supportVectors, svm_model model) {
		int[] labels = new int[supportVectors.length];
		for (int i = 0; i < supportVectors.length; ++i) {
			labels[i] = (int) svm.svm_predict(model, supportVectors[i]);
		}
		return labels;
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

}
