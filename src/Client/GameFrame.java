package Client;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
    public GameFrame(){//this is the frame the window that the game is within-- the content itself is in the GamePanel
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();//takes JFrame and fit it with the components we add to the frame
        this.setVisible(true);
        this.setLocationRelativeTo(null);//makes the game appear in the middle of the screen;

    }
}
