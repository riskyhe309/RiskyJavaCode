import java.util.HashSet;
import java.util.Iterator;


/**
 * Created by Risky on 2014/8/8.
 */
public class SimpleSMO {

    double[][] x;
    int[] y;

    HashSet<Integer> boundAlpha = new HashSet<Integer>();

    double[] a;          //the Lagrange multiplier
    double b = 0.0;     // the offset of the hyperplane

    double[][] kernel;

    public SVMModel train(double[][] x, int[] y) {
        this.x = x;
        this.y = y;
        kernel = new double[x.length][x.length];
        initialKernel();


        /*
        *set the param
        * */

        double C = 10;                  //penalty ratio
        double tolarence = 0.001;       //
        double maxIter = 5;             // the max iterator when the multipliers are not changed

        /*
        * initial the Lagrange multiplier
        * */
        a = new double[x.length];
        for (int i = 0; i < a.length; i++) {
            a[i] = 0;
        }

        /*
        * begin the smo
        * */

        int iterator = 0;

        while (iterator < maxIter) {

            int numChange = 0;      // the number of iterator when the multipliers are not changed

            for (int i = 0; i < x.length; i++) {

                double Ei = getE(i);

                /*
                * select the first ai which is against the KKT condition
                *
                * the KKT condition
                *
                * yi*f(i) >= 1 and alpha == 0 (right classification)
				* yi*f(i) == 1 and 0<alpha < C (the boundary sv)
				* yi*f(i) <= 1 and alpha == C (sv between the boundary )
				*
				* ri = y[i] * Ei = y[i] * f(i) - y[i]^2 >= 0
				*
				* if ri < 0 and alpha < C it is against the KKT condition
				*
				* so as this  if ri > 0 and alpha > 0 it is also against the KKT condition
                *
                * */

                if ((Ei * y[i] < -tolarence && a[i] < C) || (y[i] * Ei > tolarence && a[i] > 0)) {

                    /*
                    * find the max|E1 - E2|
                    * */

                    int j;

                    /*
                     * boundAlpha load the set of multiplier a whose corresponding
                     * point x  is at the  boundary
                     *
                     * if the BSVj can gain the maxE then choose the BSVj
                     * else random choose the point which is not equal i
					 *
					 */

                    if (!this.boundAlpha.isEmpty()) {
                        j = findMax(Ei, boundAlpha);
                    } else {
                        j = RandomChoose(i);
                    }

                    double Ej = getE(j);

                    //store the old a
                    double oldAi = a[i];
                    double oldAj = a[j];

                    //compute the range of multiplier

                    double L, H;
                    if (y[i] != y[j]) {
                        L = Math.max(0, a[j] - a[i]);
                        H = Math.min(C, C + a[j] - a[i]);
                    } else {
                        L = Math.max(0, a[j] + a[i] - C);
                        H = Math.min(C, a[j] + a[i]);
                    }

                    double eta = kernel[i][i] + kernel[j][j] - 2 * kernel[i][j];

                    if (eta <= 0)
                        continue;

                    a[j] = a[j] + y[j] * (Ei - Ej) / eta;


                    if (0 < a[j] && a[j] < C)
                        this.boundAlpha.add(j);

                    if (a[j] < L)
                        a[j] = L;
                    else if (a[j] > H)
                        a[j] = H;


                    if (Math.abs(a[j] - oldAj) < 1e-5)
                        continue;

                    a[i] = a[i] + y[i] * y[j] * (oldAj - a[j]);


                    double b1 = b - Ei - y[i] * kernel[i][i] * (a[i] - oldAi)
                            - y[j] * kernel[j][i] * (a[j] - oldAj);
                    double b2 = b - Ej - y[i] * kernel[i][j] * (a[i] - oldAi)
                            - y[j] * kernel[j][j] * (a[j] - oldAj);


                    if (0 < a[i] && a[i] < C) {
                        this.boundAlpha.add(i);
                        b = b1;
                    } else if (0 < a[j] && a[j] < C) {
                        b = b2;
                    } else
                        b = (b1 + b2) / 2;

                    numChange++;
                }

            }

            if (numChange == 0)
                iterator++;
            else
                iterator = 0;
        }
        return new SVMModel(a, b, y,x);
    }



    /**
     * random choose j
     */
    private int RandomChoose(int i) {
        int j;
        do {
            j = (int) Math.random() * x.length;
        } while (j == i);

        return j;
    }


    /**
     * find the j which make the |Ej-Ei| maximized
     * while the j is BSV
     */
    private int findMax(double Ei, HashSet<Integer> alpha) {
        double max = 0;
        int index = -1;
        Iterator<Integer> it = alpha.iterator();
        while (it.hasNext()) {
            Integer j = it.next();
            double Ej = getE(j);
            if (Math.abs(Ei - Ej) > max) {
                max = Math.abs(Ei - Ej);
                index = j;
            }
        }

        return index;
    }

    /**
     * compute the Ei = g(xi) - y[i]
     */
    private double getE(int i) {

        return f(i) - y[i];
    }

    /**
     * compute the g(Xi)
     */
    private double f(int i) {
        double sum = 0;
        for (int j = 0; j < x.length; j++)
            sum += a[j] * y[j] * kernel[i][j];
        return sum + this.b;
    }


    /**
     * initiate the kernel array
     */
    private void initialKernel() {
        for (int i = 0; i < x.length; i++) {
            for (int j = i; j < x.length; j++) {
                kernel[j][i] = kernel[i][j] = k(x[i], x[j]);
            }
        }
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
