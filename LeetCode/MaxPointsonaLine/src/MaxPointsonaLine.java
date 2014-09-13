import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by RISKYHE on 2014/5/6.
 */
public class MaxPointsonaLine {

    public static void main(String[] args) {

        Point[] s = {new Point(-4, 0), new Point(-4, -1), new Point(-4, 5)
                , new Point(9, 25)
                };


        Solution sl = new Solution();


        System.out.println(sl.maxPoints(s));

    }


    /**
     * Definition for a point.
     */
    static class Point {
        int x;
        int y;

        Point() {
            x = 0;
            y = 0;
        }

        Point(int a, int b) {
            x = a;
            y = b;
        }
    }

    public static class Solution {
        public int maxPoints(Point[] points) {

            if (points.length < 3) {

                return points.length;
            }

            // store the slope and the num of the line( which has the slope)

            int maxPoint = 0;

            for (int i = 0; i < points.length - 1; i++) {


                HashMap<Double, Integer> line = new HashMap<Double, Integer>();

                int total = 1;
                int vertical = 0;  //the slope is infinite;


                // just compute the point which is located the right of i
                // because if the left point is in the line ,then ,we just compute
                //the line(include the left point,the centre,and some right point )
                // in the previous lope;
                for (int j = i + 1; j < points.length; j++) {

                    if (points[j].y == points[i].y) {

                        if (points[j].x == points[i].x) {        //the repetition point
                            total++;
                            continue;
                        } else {                                   //the vertical line
                            vertical++;
                            continue;
                        }
                    }

                    double slope =(double) (points[j].x - points[i].x)
                             / (points[j].y - points[i].y) + 0.0 ;  // avoid -0.0

                    System.out.println(slope+"   slope  "+i);
                    Integer value = line.get(slope);

                    if (value == null) {
                        line.put(slope, 1);
                    } else {
                        line.put(slope, value + 1);
                    }


                }

                // find the max number of slop, begin with vertical
                Set<Double> set = line.keySet();
                Iterator<Double> it = set.iterator();

                while (it.hasNext()) {
                    Double tem = it.next();
                    Integer num = line.get(tem);

                    if (vertical < num) {
                        vertical = num;
                    }
                }
                // vertical is the maxNum
                vertical += total;

                //   System.out.println(vertical);
                if (maxPoint < vertical) {
                    maxPoint = vertical;
                    System.out.println(maxPoint);
                }
            }
            return maxPoint;
        }
    }


}
