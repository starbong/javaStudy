package P269_추상클래스;

abstract class Shape {  //추상클래스가 된다 draw 때문!
    int x, y;
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public abstract void draw();    //추상메소드 선언, 추상 메소드를 하나라도 가지면 추상 클래스가 된다. 추상 메소드를 가지고 있는데도
    //                                abstract 를 class 앞에 붙이지 않으면 컴파일 오류가 발생
};
public class P269 extends Shape {
    int width, height;
    public void draw() {        //추상 메소드 구현
        System.out.println("사각형 그리기 메소드");  //서브 클래스 P269에서 수퍼 클래스 추상 메소드 draw()가 실제 메소드를 구현
        //                                          서브클래스에서 추상 메소드를 구현하지 않으면 컴파일 오류 발생
    }
};
class circle extends Shape {
    int radius;
    public void draw() {
        System.out.println("원 그리기 메소드");    //추상메소드 draw()가 실제 메소드로 구현한다.
    }
}
