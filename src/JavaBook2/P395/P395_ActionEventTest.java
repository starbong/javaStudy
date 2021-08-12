package JavaBook2.P395;

import javax.swing.*;
import java.awt.*;

class MyFrame  extends JFrame {  //프레임 상속해서 MyFrame 선언
   private JButton button;
    public MyFrame() {  //생성자 컴포넌트를 생성하고 추가한다.
        this.setSize(300,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("이벤트예제");
        this.setLayout(new FlowLayout());
        button = new JButton("버튼을 누르시오");
        button.addActionListener(new MyListener()); //버튼에 이벤트 리스너 등록

        this.add(button);
        this.setVisible(true);
        //이벤트리스너 객체를 new 를 이용하여서 생성하고 버튼에 이벤트 리스너 객체를 등록
        //...
    }
}
public class P395_ActionEventTest {
    public static void main(String[] args) {
        MyFrame t = new MyFrame();
    }
}
