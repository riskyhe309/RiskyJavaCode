import java.io.*;

/**
 * Created by Risky on 2014/9/3.
 */
public class Normalize {


    public static void main(String[] args) throws IOException {

        String input = "C:\\Users\\Risky\\Desktop\\STDpendigits";
        String output = "C:\\Users\\Risky\\Desktop\\Nompendigits";


        BufferedReader br = new BufferedReader(new FileReader(new File(input)));
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(output)));


        String line = null;

        double[][] data = new double[7494][16];
        int index = 0;

        while ((line = br.readLine()) != null) {

            String[] str = line.split("\t");

            for (int i = 2; i < 18; i++) {
                data[index][i-2] = Double.parseDouble(str[i]);
            }

            index++;

        }

        double[] max = new double[16];
        double[] min = new double[16];

        for (int i = 0; i < data.length; i++) {

            for (int j = 0; j < 16; j++) {

                if (max[j] < data[i][j]) {
                    max[j] = data[i][j];
                }

                if (min[j] > data[i][j]) {
                    min[j] = data[i][j];
                }
            }
        }

        for (int i = 0; i < data.length; i++) {

            StringBuilder sb = new StringBuilder();
            sb.setLength(0);
            sb.append(data[i][0] + "\t" + data[i][1] + "\t");

            for (int j = 0; j < 16; j++) {
                if (min[j] != max[j])
                    data[i][j] = (data[i][j] - min[j]) / (max[j] - min[j]);
                sb.append(data[i][j] + "\t");
            }

            bw.write(sb.toString());
            bw.newLine();
        }

        for (int m = 0; m < 16; m++) {
            System.out.println(max[m] + "\t" + min[m]);
        }


        br.close();
        bw.flush();
        bw.close();


    }
}
