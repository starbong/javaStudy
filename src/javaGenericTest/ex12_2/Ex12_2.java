package javaGenericTest.ex12_2;

import java.util.ArrayList;
import java.util.Iterator;

class Student {
    String name = "";
    int ban;
    int no;

    Student(String name, int ban, int no) {
        this.name = name;
        this.ban = ban;  //반
        this.no = no; //번호
    }
}

public class Ex12_2 {
    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<Student>();
        list.add(new Student("자바왕", 1, 1));
        list.add(new Student("자바짱", 1, 2));
        list.add(new Student("홍길동", 2, 1));
        Iterator<Student> it = list.iterator();
//        Iterator it = list.iterator();
//        Iterator it = list.iterator();
        while (it.hasNext()) {
//          Student s = (Student) it.next();
//            Student s = it.next();
//            System.out.println(s.name);
            System.out.println(it.next().name);
        }
    }
}
