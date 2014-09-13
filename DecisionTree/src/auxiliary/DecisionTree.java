package auxiliary;


import java.util.*;


/**
 * @author daq
 */
public class DecisionTree extends Classifier {

    boolean isClassification;

    TreeNode root;

    /*
    * isCategory[k] indicates whether the kth attribut is discrete or continuous, the last attribute is the label
    * features[i] is the feature vector of the ith sample
    * labels[i] is the label of he ith sample
    */
    @Override
    public void train(boolean[] isCategory, double[][] features, double[] labels) {


        isClassification = isCategory[isCategory.length - 1];

        handleNaN(features, isCategory, labels);

        if (isClassification) { // classification

            ArrayList<Integer> al = new ArrayList<>();

            for (int i = 0; i < features[0].length; i++) {
                al.add(i);
            }


            double[][] feature = getFeatureAndLabel(features, labels);

            root = createCTree(feature, isCategory, al, 0);


        } else { // regression   	


        }
    }


    /*
    * features is the feature vector of the test sample
    * you need to return the label of test sample
    */

    @Override
    public double predict(double[] features) {
        if (isClassification) {
            return ClaPredict(features, root);

        } else {
        }

        return 0;
    }


    /*****************************************Train functions*****************************************************/


    /**
     * Handle the data
     */
    private void handleNaN(double[][] features, boolean[] isCategory, double[] labels) {
        //遍历整个features，找到NAN的，替换
        for (int i = 0; i < isCategory.length - 1; i++)
            for (int j = 0; j < labels.length; j++) {
                if (Double.isNaN(features[j][i])) {    //离散属性时，选出最多的属性值赋给NaN
                    if (isCategory[i]) {
                        ArrayList<Double> list = new ArrayList<>();
                        for (int m = 0; m < labels.length; m++) {
                            if (!list.contains(features[m][i]) && !Double.isNaN(features[m][i])) {
                                list.add(features[m][i]);
                            }
                        }
                        int[] a = new int[list.size()];
                        for (int n = 0; n < a.length; n++) {
                            a[n] = 0;
                        }
                        for (int m = 0; m < labels.length; m++) {
                            if (!Double.isNaN(features[m][i]))
                                a[list.indexOf(features[m][i])]++;
                        }
                        int n = 0;
                        for (int m = 0; m < a.length; m++) {
                            if (a[m] > a[n]) {
                                n = m;
                            }
                        }
                        features[j][i] = list.get(n);
                    } else {    //连续属性找出平均值赋给NaN
                        int a = 0;
                        double num = 0.0;
                        for (int m = 0; m < labels.length; m++) {
                            if (Double.isNaN(features[m][i])) {
                                num = features[m][i] + num;
                                a++;
                            }
                        }
                        features[j][i] = num / a;
                    }
                }
            }

    }


    /**
     * combine the feature and labels
     */

    private double[][] getFeatureAndLabel(double[][] features, double[] labels) {

        int row = features.length;
        int column = features[0].length;

        double[][] result = new double[row][column + 1];

        for (int i = 0; i < row; i++) {

            for (int j = 0; j < column; j++)
                result[i][j] = features[i][j];


            result[i][column] = labels[i];
        }

        return result;
    }


    /**
     * create the classification tree
     */
    private TreeNode createCTree(double[][] feature, boolean[] isCategory,
                                 ArrayList<Integer> al, int h) {


        TreeNode node = new TreeNode();
        //  System.out.println(h);

        boolean isComplete = isStop(feature);

        node.label = getMaxLabel(feature);

        if (al.isEmpty() || isComplete || h > 8)
            return node;


        double[] index = getMinGini(feature, al, isCategory);


        int splitVariable;
        splitVariable = (int) index[0];
        double splitPoint;
        splitPoint = index[1];

        node.isDiscret = isCategory[splitVariable];
        node.nodeName = splitVariable;
        node.splitPoint = splitPoint;

        ArrayList<double[]>[] splitList = split(splitVariable,
                splitPoint, feature, isCategory[splitVariable]);


        al.remove(new Integer(splitVariable));

        double[][] feats_1 = getArray(splitList[0]);
        double[][] feats_2 = getArray(splitList[1]);

        if (feats_1 == null || feats_2 == null)
            return node;

        node.leftChild = createCTree(feats_1, isCategory, al, h + 1);

        node.rightChild = createCTree(feats_2, isCategory, al, h + 1);

        return node;

    }


    /**
     * the stop condition
     */
    private boolean isStop(double[][] feature) {

        if (feature == null)
            return true;

        return getGini(feature) <= 0.01;

    }


    /**
     * get the max label of the features
     */
    private double getMaxLabel(double[][] features) {

        double[] label = getValues(features, features[0].length - 1);

        Map<Double, Integer> map = new HashMap<>();
        for (double d : label)
            map.put(d, 0);

        int length = features[0].length - 1;

        for (double[] feature : features)
            map.put(feature[length], map.get(feature[length]) + 1);

        int max = 0;
        double result = 0;

        Set<Double> set = map.keySet();

        for (Double d : set) {
            int value = map.get(d);
            if (value > max) {
                max = value;
                result = d;
            }

        }

        return result;
    }


    /**
     * get the min Gini coefficient and return the index of the feature;
     */
    private double[] getMinGini(double[][] feature, ArrayList<Integer> al, boolean[] isCategory) {

        double[] result = new double[2];

        double min = Double.MAX_VALUE;

        for (Integer a : al) {

            double[] values = getValues(feature, a);

            if (values.length == 1) {

                double Gini = getGini(feature);

                if (min >= Gini) {
                    min = Gini;
                    result[0] = a;
                    result[1] = values[0];
                }

            } else {

                if (isCategory[a])
                    for (int j = 0; j < values.length - 1; j++) {

                        ArrayList<double[]>[] splitAl = split(a, values[j], feature, true);

                        double[][] a_1 = getArray(splitAl[0]);
                        double[][] a_2 = getArray(splitAl[1]);


                        int totalData = a_1.length + a_2.length;

                        double Gini = (a_1.length * getGini(a_1) +
                                a_2.length * getGini(a_2)) / totalData;


                        if (min >= Gini) {
                            min = Gini;
                            result[0] = a;
                            result[1] = values[j];
                        }
                    }
                else
                    for (int j = 1; j < values.length; j++) {
                        double sp = (values[j - 1] + values[j]) / 2;

                        ArrayList<double[]>[] splitAl = split(a, sp, feature, false);

                        double[][] a_1 = getArray(splitAl[0]);
                        double[][] a_2 = getArray(splitAl[1]);

                        int totalData = a_1.length + a_2.length;

                        double Gini = (a_1.length * getGini(a_1) +
                                a_2.length * getGini(a_2)) / totalData;

                        if (min >= Gini) {
                            min = Gini;
                            result[0] = a;
                            result[1] = sp;
                        }

                    }

            }


        }
        return result;

    }


    /**
     * get the values of the ith feature
     */
    private double[] getValues(double[][] features, int index) {


        TreeSet<Double> tem_result = new TreeSet<>();

        for (double[] feature : features)
            tem_result.add(feature[index]);


        double[] result = new double[tem_result.size()];

        for (int i = 0; i < result.length; i++) {

            result[i] = tem_result.pollFirst();

        }

        return result;

    }


    /**
     * compute the Gini coefficient;
     */
    private double getGini(double[][] features) {

        if (features == null)
            return 0;

        Map<Double, Integer> map = new HashMap<>();
        int length = features[0].length - 1;

        for (double[] temp : features) {

            double label = temp[length];
            Integer value = map.get(label);
            if (value == null)
                map.put(label, 1);
            else
                map.put(label, value + 1);
        }

        double sigma = 0;

        Set<Double> set = map.keySet();
        for (Double d : set) {
            Integer value = map.get(d);
            sigma += value * value;
        }

        return (1 - sigma / (features.length * features.length));
    }


    /**
     * split the data set with the ath feature and the split poinit is d
     */
    private ArrayList<double[]>[] split(int a, double d, double[][] feature, boolean isDis) {

        ArrayList<double[]>[] result;

        result = new ArrayList[2];

        result[0] = new ArrayList<>();
        result[1] = new ArrayList<>();

        for (double[] aFeature : feature) {

            if (!isDis) {
                if (aFeature[a] <= d)
                    result[0].add(aFeature);
                else
                    result[1].add(aFeature);
            } else {
                if (aFeature[a] == d)
                    result[0].add(aFeature);
                else
                    result[1].add(aFeature);
            }
        }

        return result;

    }


    /**
     * transform the ArrayList to Array
     */
    private double[][] getArray(ArrayList<double[]> doubles) {

        if (doubles.isEmpty())
            return null;

        double[][] result = new double[doubles.size()][doubles.get(0).length];
        int i = 0;
        for (double[] temp : doubles) {

            for (int j = 0; j < temp.length; j++)
                result[i][j] = temp[j];

            i++;
        }

        return result;
    }


    /********************************************Predict functions****************************************************/

    /**
     * retrieval the tree
     */
    private double ClaPredict(double[] features, TreeNode node) {


        if (node.leftChild == null)
            return node.label;

        if (node.isDiscret) {
            if (features[node.nodeName] == node.splitPoint)
                return ClaPredict(features, node.leftChild);
            else
                return ClaPredict(features, node.rightChild);
        } else {
            if (features[node.nodeName] <= node.splitPoint)
                return ClaPredict(features, node.leftChild);
            else
                return ClaPredict(features, node.rightChild);
        }


    }


    /**
     * *******************************************Tree Node*********************************************************
     */
    public class TreeNode {
        int nodeName;
        double splitPoint;
        double label;
        TreeNode leftChild;
        TreeNode rightChild;
        boolean isDiscret;
    }


}



