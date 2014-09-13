import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Risky on 2014/6/30.
 */
public class Server {

    public static void  main(String[] args){


        try {
            ServerSocket ss = new ServerSocket(8899);

            while(true)
            {
                Socket s=ss.accept();

                String ip = s.getInetAddress().getHostAddress();
                System.out.println(ip+"......connected");

                BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String line = bufIn.readLine();
                System.out.println(line);


                OutputStream os=s.getOutputStream();
                os.write("lalala\n".getBytes("utf-8"));
                //os.flush();
                os.close();
                s.close();
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
