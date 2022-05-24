package view;

import javax.swing.*;

public class Difficulty {

    private JFrame myFrame;
    private ImageIcon myWindowIcon;
    private final int myFrameWidth = 500, myFrameHeight = 500;
    private final String myWindowIconPath = "assets\\App_Icon.png";



    public Difficulty() {
        myFrame = new JFrame();
        myFrame.setTitle("Trivia Maze");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myWindowIcon = new ImageIcon(myWindowIconPath);
        myFrame.setIconImage(myWindowIcon.getImage());
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);
        myFrame.setSize(myFrameWidth, myFrameHeight);
        myFrame.setVisible(true);
//        myFrame.setLayout();
    }

    public static void main(String[] theArgs) {
        Difficulty difficulty = new Difficulty();
    }
}
