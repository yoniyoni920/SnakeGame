package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {//where the actual game content is.
    static final int SCREEN_WIDTH= 600;
    static final int SCREEN_HEIGHT= 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;//Amount of objects we can fit in screen
    static final int Delay = 75;//Delay for timer- higher number slower the game
    static final int INITIAL_BODY_SIZE = 6;
    static final int APPLE_COUNTER = 0;
    final int x[] = new int[GAME_UNITS];// holds x coordinates of snakes body parts includes head
    final int y[] = new int[GAME_UNITS];// holds y coordinates of snakes body parts includes the head
    int bodyParts = INITIAL_BODY_SIZE;
    int applesEaten=APPLE_COUNTER;
    int appleX;//Used to place X coordinate of new apple.
    int appleY;//Used to place Y coordinate of new apple.
    char direction='R';
    boolean running = false;
    Timer timer;
    Random random;
    public GamePanel() {
        random = new Random();
        this.setPreferredSize((new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT)));
        //tells where the GamePanels activities will happen so the game can play throughout the whole place
        // would have had a bigger impact if we wanted to split screen to several different active panels
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(Delay,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        if(running) {
               for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                  g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                   g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                }
                g.setColor(Color.red);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

                for (int i = 0; i < bodyParts; i++) {
                    if (i == 0) {
                        g.setColor(Color.green);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                     } else {
                       g.setColor(new Color(45, 180, 0));
                       g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                       g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.RED);
            g.setFont( new Font("Int Free",Font.BOLD,40));
            FontMetrics metricts = getFontMetrics(g.getFont());//used to line text in the middle of the screen
            g.drawString("Score: " + applesEaten,(SCREEN_WIDTH - metricts.stringWidth("Score: " + applesEaten))/2,g.getFont().getSize());

        }
        else{
            gameOver(g);
        }

    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*(UNIT_SIZE);
        //why do you multiply by UNIT_SIZE at the end?
        //SCREEN_WIDTH/UNIT_SIZE gives the x location for where you can place so lets say its 15/3=5
        // lets say we got 3 so we need to place apple on the third x but the third starts at 9 because each
        // square is of size 3 so 3*3
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*(UNIT_SIZE);
    }
    public void move(){
        for(int i = bodyParts;i>0;i--){
            x[i] = x[i-1];//starts from the tail deletes it and moves to next location
            y[i] = y[i-1];
        }
        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }
    public void checkApple(){
        if((x[0])==appleX && (y[0] == appleY)){
          bodyParts++;
          applesEaten++;
          newApple();
        }

    }
    public void checkCollisions() {
        //check if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
            // check if head touches borders
            if ((x[0] < 0) || (x[0] > SCREEN_WIDTH) || (y[0] < 0) || (y[0] > SCREEN_HEIGHT)) {
                running = false;
            }
        }
            if (!running) {
                timer.stop();
            }

    }
    public void gameOver(Graphics g){
        g.setColor(Color.RED);
        g.setFont( new Font("Int Free",Font.BOLD,75));
        FontMetrics metricts = getFontMetrics(g.getFont());//used to line text in the middle of the screen
        g.drawString("Game Over",(SCREEN_WIDTH - metricts.stringWidth("Game Over"))/2,((SCREEN_HEIGHT)/2));

        g.setColor(Color.RED);
        g.setFont( new Font("Int Free",Font.BOLD,40));
        FontMetrics metricts2 = getFontMetrics(g.getFont());//used to line text in the middle of the screen
        g.drawString("Score: " + applesEaten,(SCREEN_WIDTH - metricts2.stringWidth("Score: " + applesEaten))/2,g.getFont().getSize());

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;

            }

        }
    }
}
