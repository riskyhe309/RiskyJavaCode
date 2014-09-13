import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Risky on 2014/6/30.
 */
public class Client {


    public static void main(String[] args) {


        try {
            Socket s = new Socket("127.0.0.1",8899);


            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            pw.println("Hello,Server!");



            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));


            String L = null;

            while ((L = br.readLine()) != null) {
                System.out.println(L);
            }

            s.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

