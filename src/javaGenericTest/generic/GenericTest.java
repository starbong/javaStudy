package javaGenericTest.generic;

import java.util.ArrayList;
class Tv{}
class Audio{}
public class GenericTest {
    public static void main(String[] args) {
        ArrayList<Tv> list = new ArrayList<Tv>();
        list.add(new Tv());

        Tv t = list.get(0);
    }
}
