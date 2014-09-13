import java.io.*;

/**
 * Created by Risky on 2014/9/3.
 */
public class Read {

    public static void main(String[] args) throws IOException {

        String input = "C:\\Users\\Risky\\Desktop\\pendigits.t";
        String output = "C:\\Users\\Risky\\Desktop\\STDpendigits.t";

        BufferedReader br = new BufferedReader(new FileReader(new File(input)));
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(output)));


        String line = null;

        int index = 0;

        while ((line = br.readLine()) != null) {

            StringBuilder sb = new StringBuilder();
            sb.setLength(0);

            String[] str = line.split("\\s+");

            sb.append(index + "\t");
            sb.append(str[0] + "\t");

            index++;

            int inx = 1;

            int length = str.length;

            for (int i = 1; i < str.length; i++) {


                String[] temp = str[i].split(":");

                while (inx < Integer.valueOf(temp[0])) {
                    sb.append(0.00 + "\t");
                    inx++;
                }

                sb.append(Double.parseDouble(temp[1])/100 + "\t");
                inx++;

            }

            for (int j = inx; j < 17; j++)
                sb.append(0.00 + "\t");

            bw.write(sb.toString());
            bw.newLine();
        }

        br.close();
        bw.flush();
        bw.close();

    }

}
