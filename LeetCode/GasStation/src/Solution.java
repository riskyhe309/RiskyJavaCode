/**
 * Created by RISKYHE on 2014/6/9.
 */
public class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {

        int state = 0;
        int total = 0;
        int result = -1;
        for (int i = 0; i < gas.length; i++) {

            state += gas[i] - cost[i];
            total += gas[i] - cost[i];

            if (state < 0) {            //if the state is negative at ith station,
                // then from 0 to i-1, the
                // state will be negative when the car run to
                // ith station (the ith station is first station
                // that state is negative), so we can omit those

                result = i;
                state = 0;
            }
        }
        return total > -1 ? result + 1 : -1;
    }
}