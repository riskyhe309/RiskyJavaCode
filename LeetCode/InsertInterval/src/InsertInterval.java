import java.util.ArrayList;

/**
 * Created by RISKYHE on 2014/5/12.
 */
public class InsertInterval {

    public static void main(String[] args) {


        ArrayList<Interval> al = new ArrayList<Interval>();

        Interval ii1 = new Interval(1, 2);
        Interval ii2 = new Interval(3, 5);
        Interval ii3 = new Interval(6, 7);
        Interval ii4 = new Interval(8, 10);
        Interval ii5 = new Interval(12, 16);
        Interval ii6 = new Interval(4, 9);

        al.add(ii1);
        al.add(ii2);
        al.add(ii3);
        al.add(ii4);
        al.add(ii5);

        Solution sl = new Solution();

        al = sl.insert(al,ii6);

        for(Interval i :al){

            System.out.println(i.start+"   "+i.end);
        }

    }




}

