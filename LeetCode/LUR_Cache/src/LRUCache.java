import java.util.AbstractMap;
import java.util.HashMap;

/**
 * Created by RISKYHE on 2014/6/8.
 */
public class LRUCache {



    private int capacity = 0;
    private SimpleList doubleList = new SimpleList();
    private HashMap<Integer,Node> map = new HashMap<Integer, Node>();


    public LRUCache(int capacity) {
        this.capacity = capacity;

    }

    public int get(int key) {
        if (map.containsKey(key)){
            Node n = map.get(key);
            doubleList.delete(n);
            doubleList.insert(n);
            return n.value;
        }
        else
            return -1;

    }

    public void set(int key, int value) {

        //the key is in the Cache
        if (map.containsKey(key)){

            Node n = map.get(key);
            //delete the node
            doubleList.delete(n);
            //refresh the value and insert it;
            n.value = value;
            doubleList.insert(n);
        }
        else{
            if (map.size() == capacity){
                Node n = new Node();
                n.value = value;
                n.key = key;
                Node last = doubleList.head.pre;
                //renew the map and list
                doubleList.delete(last);
                doubleList.insert(n);
                map.remove(last.key);
                map.put(key,n);
            }
            else{
                Node n = new Node();
                n.value = value;
                n.key = key;
                doubleList.insert(n);
                map.put(key,n);
            }
        }

    }
}
