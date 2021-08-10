package P290_무명클래스;

public class AnonymousClassTest {
    public static void main(String[] args) {
        RemoteControl ac = new RemoteControl() {
            @Override
            public void turnOn() {
                System.out.println("TV turnOn");
            }

            @Override
            public void turnOff() {
                System.out.println("TV turnOff");

            }
        };
        ac.turnOn();
        ac.turnOff();

    }
}
