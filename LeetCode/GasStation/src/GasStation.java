/**
 * Created by RISKYHE on 2014/6/9.
 */
public class GasStation {

    public static void main(String[] args){

        int[] gas = {0,4,5};
        int[] cost = {1,2,6};

        Solution sl = new Solution();

        System.out.println(sl.canCompleteCircuit(gas,cost));
    }


}

