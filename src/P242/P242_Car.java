package P242;

public class P242_Car {
    int speed;
    int gear;
    public String color
            ;

    public void speedUp(int increment){ //속도 증가 메소드
        speed += increment;
    }
    public void speedDown(int decrement){   //속도 감소 메소드
        speed -= decrement;
    }
}
