import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;


/**
 * Created by RISKYHE on 14-3-23.
 */
public class ReadDatadate
{

    //����ͳ��user����Ϣ���Ұ�����.����Ƕ��map����û���brand��ϲ�ó̶ȡ�
    // ���尴�������㣬���е��3�֣�����5�֣��ղ�8�֣����ﳵ13�֣�쳲���������^-^��

	private static Integer[] grade = {3,5,8,13};
    private static LinkedHashMap<String,LinkedHashMap<String,double>> userInfo;

    public static LinkedHashMap<String,LinkedHashMap<String,double>> readFile()
    {
        /**
         * ��ȡ�û���ݣ���ת���������user��
         * */

            try
            {
                String fileName = "t_alibaba_data.csv";

                userInfo = new LinkedHashMap<String,LinkedHashMap<String,double>>();

                File file = new File(fileName);
                BufferedReader userReader = new BufferedReader(new FileReader(file));

                String line;

                userReader.readLine();          //��һ�з���ݿն�

                while((line=userReader.readLine())!=null)
                {
                    String[] str = line.split(",");  //�������з�
                    storeToMap(str);
                }
                userReader.close();

            } catch (IOException e)
            {
                e.printStackTrace();
            }

        return userInfo;

    }


    /**
     * ʹ�ö�ά��Map�����ĳһ��userID���û���ĳһbrandID��Ʒ�Ƶ�����
     *
     * */
    private static void storeToMap(String[] score)
    {

        //������ת��Ϊ����
    	//�������ʼ���ڣ�ʱ��ֻ�ǰ���ÿ��31����ģ���ʼ��4��15�գ���ֹ��8��14�����125�죬��������趨
    	//����excel���֮���� 12154500��1758��0��2014/4/15����������Ҳ��ֱ�ӷָ������
    	Integer idScore = grade[Integer.parseInt(score[2])];
        //�ָ���º���
        String [] Dates = score[3].split("/");
        //��Ϊ��4�¿�ʼ�ģ���һ����15��
        int month = Integer.parseInt(Dates[1]);
        int day = Integer.parseInt(Dates[2]);
        int dayNum = (month-4)*31+day-15;
        //����1�Ƿ�ֹ����0
        double percent = (dayNum + 1)/125;
        //Map���Ѿ��и��û������ּ�¼
        if(userInfo.containsKey(score[0]))
        {
            LinkedHashMap<String,double> tempMap = userInfo.get(score[0]);

            double value = tempMap.get(score[1]);

            //�����û��Ը�Ʒ�Ƶ�����
            if(value != null)
            {
                //���û���ͬһƷ�Ƶ��������
		//�������ˣ�ʹidScore����percent 
                value = value + (double)idScore *(double) percent;
            }
            //û���û��Ը�Ʒ�Ƶ�����,ֱ�Ӵ������Ŀ������
            else
            {
		//�������ˣ�ʹidScore����percent
                value = (double)idScore *(double) percent;
            }

            tempMap.put(score[1],value);                            // ������д���û������б�

        }
        //û�и��û������ּ�¼
        else
        {
            LinkedHashMap<String,double> tempMap = new LinkedHashMap<String,double>();

            tempMap.put(score[1],(double)idScore);   //�����û�������

            userInfo.put(score[0], tempMap);

        }
    }
}
