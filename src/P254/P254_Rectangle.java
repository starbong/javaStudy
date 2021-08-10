package P254;

import java.awt.*;
class Shape {
    public Shape(String msg) {
        System.out.println("Shape 생성자()" + msg);
    }
}
public class P254_Rectangle extends Shape {
    public P254_Rectangle() {
        super("MSG");    //슈퍼 메소드를 명시해줘야함
        System.out.println("Rectangle 생성자()");
    }
}
