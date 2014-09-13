import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;


/**
 * Created by RISKYHE on 14-3-23.
 */
public class ReadData {

    //用于统计user的信息，且按序存放.并且嵌套map存放用户对brand的喜好程度。
    // 具体按评分来算，其中点击3分，购买5分，收藏8分，购物车13分（斐波那契数列^-^）

    private static Integer[] grade = {3, 5, 8, 13};
    private static LinkedHashMap<String, LinkedHashMap<String, Integer>> userInfo;

    public static LinkedHashMap<String, LinkedHashMap<String, Integer>> readFile() {
        /**
         * 读取用户数据，并转成数组放在user中
         * */

        try {
            String fileName = "F:\\JavaUse\\ALiTest\\t_alibaba_data.csv";

            userInfo = new LinkedHashMap<String, LinkedHashMap<String, Integer>>();

            File file = new File(fileName);
            BufferedReader userReader = new BufferedReader(new FileReader(file));

            String line;

            userReader.readLine();          //第一行非数据空读

            while ((line = userReader.readLine()) != null) {
                String[] str = line.split(",");  //按逗号切分
                storeToMap(str);

            }
            userReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;

    }


    /**
     * 使用二维的Map来存放某一个userID的用户对某一brandID的品牌的评价
     */
    private static void storeToMap(String[] score) {

        //将动作转换为评分
        Integer idScore = grade[Integer.parseInt(score[2])];

        //Map中已经有该用户的评分记录
        if (userInfo.containsKey(score[0])) {
            LinkedHashMap<String, Integer> tempMap = userInfo.get(score[0]);

            Integer value = tempMap.get(score[1]);

            //已有用户对该品牌的评分
            if (value != null) {
                //将用户对同一品牌的评分相加
                value = value + idScore;
            }
            //没有用户对该品牌的评分,直接存入该条目的评分
            else {
                value = idScore;
            }

            tempMap.put(score[1], value);                            // 将评分写入用户评分列表

        }
        //没有该用户的评分记录
        else {
            LinkedHashMap<String, Integer> tempMap = new LinkedHashMap<String, Integer>();

            tempMap.put(score[1], idScore);   //加入用户的评分

            userInfo.put(score[0], tempMap);

        }
    }
}
