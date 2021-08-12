package Game만들기.벽돌깨기게임;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLOutput;

public class BlockGame {
    static class MyFrame extends JFrame {
        //      constant
//      미리 상수로 지정해놓고 사용하기! 나중에 변수를 사용하다보면 중간중간 바뀔수 있기 때문에 상수로 지정해 놓는것이 편함
        static int BALL_WIDTH = 20;
        static int BALL_HEIGHT = 20;
        static int BLOCK_ROWS = 5;
        static int BLOCK_COLUMNS = 10;
        static int BLOCK_WIDTH = 40;
        static int BLOCK_HEIGHT = 20;
        static int BLOCK_GAP = 3;
        static int BAR_WIDTH = 80;
        static int BAR_HEIGHT = 20;
        static int CANVAS_WIDTH = 420 + (BLOCK_GAP * BLOCK_COLUMNS) - BLOCK_GAP;
        static int CANVAS_HEIGHT = 600;


        //variable
        static MyPanel myPanel = null;
        static int score = 0;
        static Timer time = null;
        static Block[][] blocks = new Block[BLOCK_ROWS][BLOCK_COLUMNS]; //블록공간만 만들어준거임
        static Bar bar = new Bar();
        static Ball ball = new Ball();
        static int barXTarget = bar.x;      //Target Value = interpolation
        static int dir = 0; //0: Up-Right 1 : Down-right 2 : Up-Left 3 :  Down -Left 4
        static int ballSpeed = 5;


        static class Ball {
            int x = CANVAS_WIDTH / 2 - BALL_WIDTH / 2;
            int y = CANVAS_HEIGHT / 2 - BALL_HEIGHT / 2;
            int width = BALL_WIDTH;
            int height = BALL_HEIGHT;

        }

        static class Bar {      //좌우로 움직이면서 공을 튕겨내는 바 생성
            int x = CANVAS_WIDTH / 2 - BAR_WIDTH / 2;
            int y = CANVAS_HEIGHT - 100;
            int width = BAR_WIDTH;
            int height = BAR_HEIGHT;
        }

        static class Block {    //위에 생기는 블록들
            int x = 0;      //초기화를 상수로 하지않는 이유: 블록들이 다 다르기 때문에 나중에 코딩으로 선언해줘야함
            int y = 0;
            int width = BLOCK_WIDTH;
            int height = BLOCK_HEIGHT;
            int color = 0;  //0:white 1:yellow 2:blue 3:mazanta 4:red
            boolean isHidden = false;       //충돌후에 사라지게 하는것 after collision, block will be hidden.
        }

        static class MyPanel extends JPanel {   //VANVAS for Draw!
            public MyPanel() {
                this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
                this.setBackground(Color.BLACK);
            }


            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;

                drawUI(g2d);
            }

            private void drawUI(Graphics2D g2d) {
                // draw Blocks 를 그려볼 예정
                for (int i = 0; i < BLOCK_ROWS; i++) {
                    for (int j = 0; j < BLOCK_COLUMNS; j++) {
                        if (blocks[i][j].isHidden) {
                            continue;
                        }
                        if (blocks[i][j].color == 0) {
                            g2d.setColor(Color.WHITE);
                        } else if (blocks[i][j].color == 1) {
                            g2d.setColor(Color.YELLOW);
                        } else if (blocks[i][j].color == 2) {
                            g2d.setColor(Color.BLUE);
                        } else if (blocks[i][j].color == 3) {
                            g2d.setColor(Color.MAGENTA);
                        } else if (blocks[i][j].color == 4) {
                            g2d.setColor(Color.RED);
                        }
                        g2d.fillRect(blocks[i][j].x, blocks[i][j].y, blocks[i][j].width, blocks[i][j].height);
                        //fillRect 네모를 그리는 메소드
                    }
                    //draw score
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
                    g2d.drawString("Score : " + score, CANVAS_WIDTH / 2 - 30, 20);

                    //draw Ball
                    g2d.setColor(Color.WHITE);
                    g2d.fillOval(ball.x , ball.y , BALL_WIDTH,BALL_HEIGHT);

                    //draw Bar
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(bar.x,bar.y,BAR_WIDTH,BAR_HEIGHT);
                }
            }
        }

        public MyFrame(String title) {
            super(title);
            this.setVisible(true);
            this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
            this.setLocation(400, 300);
            this.setLayout(new BorderLayout());
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            initData();
            myPanel = new MyPanel();
            this.add("Center", myPanel);
            setKeyListener();
            startTimer();
        }

        public void initData() {
            for (int i = 0; i < BLOCK_ROWS; i++) {
                for (int j = 0; j < BLOCK_COLUMNS; j++) {
                    blocks[i][j] = new Block();
                    blocks[i][j].x = BLOCK_WIDTH * j + BLOCK_GAP * j;
                    blocks[i][j].y = 100 + BLOCK_HEIGHT * i + BLOCK_GAP * i;
                    blocks[i][j].width = BLOCK_WIDTH;
                    blocks[i][j].height = BALL_HEIGHT;
                    blocks[i][j].color = 4 - i; //0:white 1:yellow 2:blue 3:mazanta 4:red
                    blocks[i][j].isHidden = false;
                }
            }
        }

        public void setKeyListener() {
            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {      //키보드를 치면 e 라는 이벤트를 통해 호출된다.
                    if(e.getKeyCode()== KeyEvent.VK_LEFT){
                        System.out.println("Pressed Left Key");
                    }else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                        System.out.println("Pressed Right Key");
                    }
                }
            });
        }
        public void startTimer() {

        }

    }

    public static void main(String[] args) {
        new MyFrame("BlockGame");
    }
}
