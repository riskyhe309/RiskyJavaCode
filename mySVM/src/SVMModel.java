/**
 * Created by Risky on 2014/8/8.
 */
public class SVMModel {

    public double[] a;
    public double b;
    public int[] y;
    public double[][]x;
    public SVMModel(double[] a, double b, int[] y,double[][] x) {
        this.a = a;
        this.b = b;
        this.y = y;
        this.x = x;
    }



    /**
     * predict the label
     */
    public int predict(double[] p) {

        int label = 0;

        double sum = 0;

        for (int j = 0; j < a.length; j++)
            sum += a[j] * y[j] * k(p, x[j]);

        sum += b;

        if (sum > 0)
            label = 1;
        else label = -1;


        return label;
    }


    /**
     * compute the inner product of vector i and vector j
     *
     * @param j
     * @ param i
     */
    private double k(double[] i, double[] j) {
        int sum = 0;
        for (int m = 0; m < i.length; m++)
            sum += i[m] * j[m];
        return sum;

    }

}
