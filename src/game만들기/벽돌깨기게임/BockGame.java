package game만들기.벽돌깨기게임;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BockGame {
    static class MyFrame extends JFrame {
        //constant
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
        static Timer timer = null;
        static Block[][] blocks = new Block[BLOCK_ROWS][BLOCK_COLUMNS]; //블록공간만 만들어준거임
        static Bar bar = new Bar();
        static Ball ball = new Ball();
        static int barXTarget = bar.x;      //Target Value = interpolation
        static int dir = 0; //0: Up-Right 1 : Down-right 2 : Up-Left 3 :  Down -Left 4
        static int ballSpeed = 3;


        static class Ball {
            int x = CANVAS_WIDTH / 2 - BALL_WIDTH / 2;
            int y = CANVAS_HEIGHT / 2 - BALL_HEIGHT / 2;
            int width = BALL_WIDTH;
            int height = BALL_HEIGHT;

            Point getCenter() {
                return new Point(x + (BALL_WIDTH / 2), y + (BALL_HEIGHT / 2));
            }

            Point getBottomCenter() {
                return new Point(x + (BALL_WIDTH / 2), y + BALL_HEIGHT);
            }

            Point getTopCenter() {
                return new Point(x + (BALL_WIDTH / 2), y);
            }

            Point getLeftCenter() {
                return new Point(x, y + (BALL_HEIGHT / 2));
            }

            Point getRightCenter() {
                return new Point(x + (BALL_WIDTH), y + (BALL_HEIGHT / 2));
            }
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
                    g2d.fillOval(ball.x, ball.y, BALL_WIDTH, BALL_HEIGHT);

                    //draw Bar
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(bar.x, bar.y, BAR_WIDTH, BAR_HEIGHT);
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
                public void keyPressed(KeyEvent e) {      //키보드를 치면 e 라는 이벤트를 통해 호출된다.
                    final int move = 20;
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        System.out.println("Pressed Left Key");
                        barXTarget -= 20;
                        if (bar.x < barXTarget) {  // x값이 타켓값보다 더 작은경우 키보드를 계속 눌렀을 경우
                            barXTarget = bar.x;
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        System.out.println("Pressed Right Key");
                        barXTarget += 20;
                        if (bar.x > barXTarget) {  // x값이 타켓값보다 더 작은경우 키보드를 계속 눌렀을 경우
                            barXTarget = bar.x;
                        }
                    }
                }
            });
        }

        public void startTimer() {
            timer = new Timer(1, new ActionListener() {    //스윙에서 지원해주는 Ui 타이머
                @Override
                public void actionPerformed(ActionEvent e) {    //Timer Event
                    movement();
                    checkCollision();   //wall,Bar
                    checkCollisionBlock();  //Blocks 50
                    myPanel.repaint();  //Redraw;
                }
            });
            timer.start();   // Start Timer!
        }

        public void movement() {
            if (bar.x < barXTarget) {
                bar.x += 5;
            } else if (bar.x > barXTarget) {
                bar.x -= 5;
            }

            if (dir == 0) {//0 : Up-Right
                ball.x += ballSpeed;
                ball.y -= ballSpeed;
            } else if (dir == 1) {  //1: Down-Right
                ball.x += ballSpeed;
                ball.y += ballSpeed;
            } else if (dir == 2) {  //2: Up-Left
                ball.x -= ballSpeed;
                ball.y -= ballSpeed;
            } else if (dir == 3) {  //3: Down-Left
                ball.x -= ballSpeed;
                ball.y += ballSpeed;
            }

        }

        public boolean duplRect(Rectangle rect1, Rectangle rect2) {
            return rect1.intersects(rect2); //두개의 사각형이 중복되는지 확인한다.
        }

        public void checkCollision() {
            if (dir == 0) {//0 : Up-Right
                //Wall
                if (ball.y < 0) {     //wall upper
                    dir = 1;
                }
                if (ball.x > CANVAS_WIDTH - BALL_WIDTH - BALL_WIDTH) {  //wall right
                    dir = 2;
                }
                //Bar = none

            } else if (dir == 1) {  //1: Down-Right
                //Wall
                if (ball.y > CANVAS_HEIGHT - (BALL_HEIGHT * 3)) { //wall bottom
                    dir = 0;
                }
                if (ball.x > CANVAS_WIDTH - (BALL_WIDTH * 2)) {  //wall right
                    dir = 3;
                }
                //Bar
                if (ball.getBottomCenter().y >= bar.y) {
                    if (duplRect(new Rectangle(ball.x, ball.y, ball.width, ball.height),
                            new Rectangle(bar.x, bar.y, bar.width, bar.height))) {
                        dir = 0;
                    }
                }
            } else if (dir == 2) {  //2: Up-Left
                //Wall
                if (ball.y < 0) {   //wall upper
                    dir = 3;
                }
                if (ball.x < 0) {   //wall left
                    dir = 0;
                }
                //Bar = none
            } else if (dir == 3) {  //3: Down-Left
                if (ball.y > CANVAS_HEIGHT - (BALL_HEIGHT * 3)) {  //wall bottom
                    dir = 2;
                }
                if (ball.x < 0) {
                    dir = 1;
                }
                //Bar
                if (ball.getBottomCenter().y >= bar.y) {
                    if (duplRect(new Rectangle(ball.x, ball.y, ball.width, ball.height),
                            new Rectangle(bar.x, bar.y, bar.width, bar.height))) {
                        dir = 2;
                    }
                }
            }
        }

        public void checkCollisionBlock() {

            //0: Up-Right, 1 : Down-right, 2 : Up-Left, 3 : Down -Left
            for (int i = 0; i < BLOCK_ROWS; i++) {
                for (int j = 0; j < BLOCK_COLUMNS; j++) {
                    Block block = blocks[i][j];
                    if (block.isHidden == false) {
                        if (dir == 0) {     //0: Up-Right
                            if (duplRect(new Rectangle(ball.x, ball.y, ball.width, ball.height),
                                    new Rectangle(block.x, block.y, block.width, block.height))) {
                                if (ball.x > block.x + 2 && ball.getTopCenter().x < block.x + block.width - 2
                                        && ball.y > block.y + 2 && ball.y <= block.y + block.height - 2) {
                                    //block bottom collision
                                    dir = 1;
                                } else if (ball.x > block.x + 2 && ball.getRightCenter().x < block.x + block.width - 2
                                        && ball.y > block.y + 2 && ball.y <= block.y + block.height - 2) {
                                    dir = 2;
                                }
                                block.isHidden = true;
                            }
                        } else if (dir == 1) {  //1 : Down-right
                            if (duplRect(new Rectangle(ball.x, ball.y, ball.width, ball.height),
                                    new Rectangle(block.x, block.y, block.width, block.height))) {
                                if (ball.x > block.x + 2 && ball.getBottomCenter().x < block.x + block.width - 2
                                        && ball.y > block.y + 2 && ball.y <= block.y + block.height - 2) {
                                    //block top collision
                                    dir = 0;
                                } else if (ball.x > block.x + 2 && ball.getRightCenter().x < block.x + block.width - 2
                                        && ball.y > block.y + 2 && ball.y <= block.y + block.height - 2) {
                                    dir = 3;
                                }
                                block.isHidden = true;
                            }
                        } else if (dir == 2) {  //2 : Up-Left
                            if (duplRect(new Rectangle(ball.x, ball.y, ball.width, ball.height),
                                    new Rectangle(block.x, block.y, block.width, block.height))) {
                                if (ball.x > block.x + 2 && ball.getTopCenter().x < block.x + block.width - 2
                                        && ball.y > block.y + 2 && ball.y <= block.y + block.height - 2) {
                                    //block bottom collision
                                    dir = 3;
                                } else if(ball.x > block.x + 2 && ball.getLeftCenter().x < block.x + block.width - 2
                                        && ball.y > block.y + 2 && ball.y <= block.y + block.height - 2){
                                    dir = 0;
                                }
                                block.isHidden = true;
                            }
                        } else if (dir == 3) {  //3 :  Down -Left
                            if (duplRect(new Rectangle(ball.x, ball.y, ball.width, ball.height),
                                    new Rectangle(block.x, block.y, block.width, block.height))) {
                                if (ball.x > block.x + 2 && ball.getBottomCenter().x < block.x + block.width - 2
                                        && ball.y > block.y + 2 && ball.y <= block.y + block.height - 2) {
                                    //block bottom collision
                                    dir = 2;
                                } else if(ball.x > block.x + 2 && ball.getLeftCenter().x < block.x + block.width - 2
                                        && ball.y > block.y + 2 && ball.y <= block.y + block.height - 2){
                                    dir = 1;
                                }
                                block.isHidden = true;
                            }
                        }
                    }
                }
            }
        }

    }

    public static void main(String[] args) {

        new MyFrame("BlockGame");
    }
}
