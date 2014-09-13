 import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by RISKYHE on 14-3-23.
 */
public class CollaborativeFilter
{

    private static LinkedHashMap<String, LinkedHashMap<String, Integer>> info; // 用户对品牌的评分
    private static double[][] relation; // 用户之间的相关关系
    private static String[] userID; // userID的顺序
    private static String[][] brands; // 推荐的结果数组

    public static void main(String[] args)
    {
        // 读取数据文件
        info = ReadDatadate.readFile();

       // relation = new double[info.size()][info.size()];

         //relation = UserRelation.computRelation(relation, info);
         //userID = UserRelation.getArrayId();
         //writeRelation();

        // 处理评分，针对每个用于找出其的评分高于平均值的品牌
        ;

        relation = readRelation();

        // 遍历数组，对每个用户找到与其相关性不低于0.5的用户
        // closeUser= getClose();

        // 基于亲密关系的用户做出预测
        brands = recommend();

        writebrands();

    }

    // 输出预测结果
    private static void writebrands()
    {

        File file = new File("F:\\JavaUse\\ALiTest\\Result.txt");

        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < userID.length; i++)
            {
                bw.write(userID[i]);
                bw.write("\t");

                // 最多输出5个
                int j = 0;
                for (; j < brands[i].length - 1 && j < 4; j++)
                {
                    bw.write(brands[i][j]);
                    bw.write(",");
                }

                if (j < 4)
                {
                    bw.write(brands[i][brands[i].length - 1]);
                } else
                {
                    bw.write(brands[i][j]);
                }

                bw.newLine();
            }
            bw.flush();
            bw.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // 根据亲密关系大于0.5的用户做出推荐
    // 推荐权值高的brands，权值计算：相关系数*用户评分的加和
    private static String[][] recommend()
    {

        String[][] result = new String[userID.length][];

        for (int i = 0; i < userID.length; i++)
        {

            Set<String> tempSet = new HashSet<String>(); // 存放预测的品牌

            // 存放潜在的品牌及其评分数
            LinkedHashMap<String, Double> temp = new LinkedHashMap<String, Double>();

            for (int j = 0; j < userID.length; j++)
            {
                double rate = relation[i][j];

                // 如果相关性大于0.5，则存入潜在品牌
                if (rate > 0.5)
                {

                    LinkedHashMap<String, Integer> map = info.get(userID[j]);

                    Set<String> set = map.keySet();
                    Iterator<String> it = set.iterator();

                    while (it.hasNext())
                    {
                        String str = it.next();
                        Integer value = map.get(str);

                        Double weight = rate * value;
                        // 相关系数*用户评分，然后加和
                        Double val = temp.get(str);

                        if (val == null)
                        {
                            temp.put(str, weight);
                        } else
                        {
                            temp.put(str, val + weight);
                        }
                    }

                }


            }

            // 计算用户自己评价的平均数
            double avge = getAvge(info.get(userID[i]));

            // 找出评分比平均值高的品牌，用tempBig保留
            LinkedHashMap<String, Double> tempBig = new LinkedHashMap<String, Double>();

            Set<String> setMap = temp.keySet();
            Iterator<String> itMap = setMap.iterator();
            while (itMap.hasNext())
            {

                String str = itMap.next();
                Double temval = temp.get(str);

                if (temval >= avge)
                {
                    tempBig.put(str, temval);
                }
            }
            // 按照值从大到小的顺序排序

            while (!tempBig.isEmpty())
            {

                String candidate = getMax(tempBig);
                tempSet.add(candidate);

            }

            result[i] = tempSet.toArray(new String[0]);
        }

        return result;
    }

    // 找出最大的值所对应的键
    private static String getMax(LinkedHashMap<String, Double> temp)
    {

        String result = null;

        Set<String> set = temp.keySet();
        Iterator<String> it = set.iterator();
        Double MaxValue = 0.0;
        while (it.hasNext())
        {

            String str = it.next();
            Double temVal = temp.get(str);
            if (MaxValue <= temVal)
            {
                MaxValue = temVal;
                result = str;
            }

        }
        temp.remove(result);

        return result;
    }

    // 返回某个用户对品牌评价的平均数
    private static double getAvge(LinkedHashMap<String, Integer> temp)
    {

        double sum = 0;
        int n = 0;
        Set<String> setMap = temp.keySet();
        Iterator<String> itMap = setMap.iterator();
        while (itMap.hasNext())
        {

            String str = itMap.next();
            Integer temVal = temp.get(str);
            sum = sum + temVal.doubleValue();
            n++;
        }

        sum = sum / n;

        return sum;
    }

    // // 找出相互之间有亲密关系的用户
    // private static String[][] getClose() {
    //
    // String[][] result = new String[userID.length][userID.length];
    //
    // Set<String> tempSet = new HashSet<String>();
    //
    // for (int i = 0; i < userID.length; i++) {
    // for (int j = 0; j < userID.length; j++) {
    // if (relation[i][j] > 0.3) {
    // tempSet.add(userID[j]);
    // }
    // }
    //
    // result[i] = tempSet.toArray(new String[0]);
    // }
    //
    // return result;
    // }




    // 读取relation和userID数组，其中第一行和第一列不用读取
    private static double[][] readRelation()
    {
        double[][] result = new double[info.size()][info.size()];

        userID = new String[info.size()];
        int index = 0;
        File fileRelation = new File("F:\\JavaUse\\ALiTest\\Relation.csv");
        try
        {

            BufferedReader br = new BufferedReader(new FileReader(fileRelation));

            String line;
            br.readLine(); // 空读一行

            while ((line = br.readLine()) != null)
            {

                String[] str = line.split(",");

                // 第一列是userID
                if (str[0] == null)
                    System.out.println(index);

                userID[index] = str[0];

                for (int i = 1; i < str.length; i++)
                {
                    result[index][i - 1] = Double.parseDouble(str[i]);
                }
                index++;
            }

        } catch (IOException e)
        {

            e.printStackTrace();
        }

        return result;
    }





    // 输出相关性数组，其中对角线为1
    private static void writeRelation()
    {

        File fileRelation = new File("F:\\JavaUse\\ALiTest\\Relation.csv");

        // 输出相关性数组

        BufferedWriter bw;
        try
        {
            bw = new BufferedWriter(new FileWriter(fileRelation));

            bw.write(",");
            for (int i = 0; i < userID.length; i++)
            {
                bw.write(userID[i]);
                bw.write(",");

            }
            bw.newLine();

            for (int m = 0; m < relation.length; m++)
            {

                bw.write(userID[m]);
                bw.write(",");

                for (int n = 0; n < relation[0].length; n++)
                {

                    bw.write(relation[m][n] + "");
                    bw.write(",");
                }
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}