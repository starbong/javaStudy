package javaBook2.p315_MyFrameTest;

import javax.swing.*;
import java.awt.*;

public class MyFrameTest2  extends JFrame {
    public MyFrameTest2(){
        Toolkit kit = Toolkit.getDefaultToolkit();  //현재화면의 크기를 얻는다.
        Dimension screenSize = kit.getScreenSize();     //현재화면의 크기를 얻는다.
        setSize(300,200);
        setLocation(screenSize.width /2 ,screenSize.height /2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MyFrame");
        JButton button = new JButton("버튼");
        this.add(button);
        setVisible(true);
    }
}

