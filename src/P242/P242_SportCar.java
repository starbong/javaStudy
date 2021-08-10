package P242;

import P242.P242_Car;

public class P242_SportCar extends P242_Car {    //car 메소드를 상속받음
    boolean turbo;

    public void setTurbo(boolean turbo) {   //터보모드 설정 메소드
        this.turbo = turbo;
    }
}
