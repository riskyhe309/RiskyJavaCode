import java.util.ArrayList;

/**
 * Created by RISKYHE on 2014/5/12.
 */
public class Solution {
    public ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {

        if (intervals.size() == 0){

            intervals.add(newInterval);
            return intervals;
        }

        int start = newInterval.start;
        int end = newInterval.end;

        int index = intervals.size();   //initiate the insert place

        boolean findStart = false;

        for (int i = 0; i < intervals.size(); i++) {

            Interval it = intervals.get(i);

            if (!findStart) {                   //start has not found
                if (it.start > start) {
                    index = i;
                    i--;                        // jude the end, the start is the initial value.
                    findStart = true;
                } else if (it.start <= start && it.end >= start) {
                    index = i;
                    start = it.start;
                    i--;
                    findStart = true;
                }
            } else {                  //start has found

                if (it.start > end) {  // find the end. before this interval

                    //add the new interval in the (i-1)th;

                    break;
                }

                if (it.end >= end) {
                    end = it.end;

                    intervals.remove(it);
//                    i--;

                    //add the new interval in the ith;
                    break;
                }

                intervals.remove(it);        // delete the interval between start and end
                i--;                         //delete the interval, the index will minus 1;
            }

        }


        intervals.add(index, new Interval(start, end));

        return intervals;
    }
}