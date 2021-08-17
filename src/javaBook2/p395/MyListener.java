package javaBook2.p395;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MyListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
//      ...	Action 이벤트를 처리하는 코드가 여기에 들어간다.
        JButton button = (JButton)e.getSource();
        button.setText("마침내 버튼이 눌러졌습니다.");
    }
}