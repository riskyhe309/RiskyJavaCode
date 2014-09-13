import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Risky on 2014/8/8.
 */
public class Test {

    static double[][] trainFeature;
    static int[] trainlabel;

    static List<List<Double>> feature = new ArrayList<List<Double>>();
    static List<Integer> label = new ArrayList<Integer>();

    double[][] predictFeature;

    public static void main(String[] args) throws IOException {

        String trainPath = "C:/Users/Risky/Desktop/data/train.txt";
        String predictPath = "C:/Users/Risky/Desktop/data/predict.txt";
        BufferedReader brTrain = new BufferedReader(new FileReader(new File(trainPath)));


        String trainLine = null;

        while ((trainLine = brTrain.readLine()) != null) {

            String[] line = trainLine.split("\\s+");

            label.add(Integer.valueOf(line[1]));

            ArrayList<Double> al = new ArrayList<Double>();

            for (int i = 2; i < line.length; i++)
                al.add(Double.valueOf(line[i]));

            feature.add(al);
        }

        convertData();

        SimpleSMO smo = new SimpleSMO();

        SVMModel model = smo.train(trainFeature, trainlabel);

        predict(model, predictPath);

    }

    private static void predict(SVMModel model, String predictPath) throws IOException {

        BufferedReader brPredict = new BufferedReader(new FileReader(new File(predictPath)));
        BufferedWriter wr = new BufferedWriter(new FileWriter(new File("C:/Users/Risky/Desktop/data/result.txt")));
        String trainLine = null;

        int index = 0;
        while ((trainLine = brPredict.readLine()) != null) {

            String[] line = trainLine.split("\\s+");

            // label.add(Integer.valueOf(line[1]));

            double[] pre = new double[line.length - 2];
            for (int i = 0; i < line.length - 2; i++)
                pre[i] = Double.parseDouble(line[i + 2]);

            int preLabel = model.predict(pre);

            wr.write(index +" "+preLabel + trainLine.substring(2,trainLine.length()));
            wr.newLine();
        }

        wr.close();


    }

    private static void convertData() {

        trainFeature = new double[feature.size()][];

        trainlabel = new int[feature.size()];
        for (int i = 0; i < feature.size(); i++) {
            double[] temp = new double[feature.get(i).size()];
            for (int j = 0; j < feature.get(i).size(); j++) {
                temp[j] = feature.get(i).get(j);
            }

            trainFeature[i] = temp;

            trainlabel[i] = label.get(i);
        }


    }
}
