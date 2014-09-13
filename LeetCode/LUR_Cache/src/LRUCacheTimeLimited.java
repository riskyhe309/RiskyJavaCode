

/**
 * Created by RISKYHE on 2014/5/7.
 */
public class LRUCacheTimeLimited {

    private int[][] cache;

    private boolean[] load;

    public LRUCacheTimeLimited(int capacity) {


        // the first column is key
        //the second  column is value
        //the third  column is the used num
        //the load shows whether this block is validate
        cache = new int[capacity][3];

        load = new boolean[capacity];

        //initialize the cache
        for (int i = 0; i < capacity; i++) {
            load[i] = false;
        }
    }

    public int get(int key) {

        for (int i = 0; i < cache.length; i++) {
            if (load[i] && cache[i][0] == key) {
                cache[i][2]++;          //the used num ++
                return cache[i][1];
            }
        }
        return -1;
    }

    public void set(int key, int value) {

        int index = findLRU();

        cache[index][0] = key;
        cache[index][1] = value;
        cache[index][2] = 1;
        load[index] = true;

    }


    private int findLRU() {

        int index = 0;
        int minLRU = cache[0][2];

        for (int i = 0; i < cache.length; i++) {

            if (load[i]) {
                if (minLRU > cache[i][2]) {
                    minLRU = cache[i][2];
                    index = i;
                }

            }
            else {  //this block is invalidate
                return i;
            }
        }
        return index;
    }
}