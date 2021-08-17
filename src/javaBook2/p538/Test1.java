package javaBook2.p538;

import com.sun.jdi.ObjectReference;

interface Pair<K, V> {
    public K getKey();

    public V getValue();
}

class OrderedPair<K, V> implements Pair<K, V> {
    private K key;
    private V value;

    public OrderedPair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

}
public class Test1 {
    public static <V, K> void main(String[] args) {
        Pair<String,Integer> p1 = new OrderedPair<String,Integer>("Even",8);
        Pair<String,String> p2 = new OrderedPair<String,String>("hello","world");
        System.out.println(p1);
        System.out.println(p2);
    }
}

