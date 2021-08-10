package P242;

import P242.P242_SportCar;

public class P243_Test {
    public static void main(String[] args) {
        P242_SportCar s = new P242_SportCar();
        s.color = "Red";
        s.speedUp(100);
        s.speedDown(50);
        s.setTurbo(true);
        System.out.println(s.color);
    }
}
