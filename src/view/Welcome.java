package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Welcome {

    private JFrame myFrame;
    private JPanel myPanel;
    private Image myImage;
    private JLabel myLabel;
    private final int myFrameWidth = 500, myFrameHeight = 500;
    private final String myBackground = "assets/Landing_Page_01.png";


    public Welcome() {
        myFrame = new JFrame();
        myFrame.setTitle("Trivia Maze");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);
        myFrame.setSize(myFrameWidth, myFrameHeight);
        myFrame.setVisible(true);

        myImage = Toolkit.getDefaultToolkit().getImage(myBackground);
        myPanel = new JPanel();
        myPanel.setSize(myFrameWidth, myFrameHeight);
        


    }
    private static void main(String[] args) {

    }

}
