package view;

import javax.swing.*;


public class Welcome {

    private JFrame myFrame;
    private ImageIcon myBackgroundImage;
    private ImageIcon myWindowIcon;
    private JButton myButton;
    private final int myFrameWidth = 500, myFrameHeight = 500;
    private final String myBackgroundPath = "assets\\Landing_Page_01.png", myWindowIconPath = "assets\\App_Icon.png";


    // TODO add Landing_Page_01.png to the JButton, set vertical text position to bottom, make text breath, add action listener
    public Welcome() {
        myFrame = new JFrame();
        myFrame.setTitle("Trivia Maze");
        myWindowIcon = new ImageIcon(myWindowIconPath);
        myFrame.setIconImage(myWindowIcon.getImage());
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);
        myFrame.setSize(myFrameWidth, myFrameHeight);
        myFrame.setVisible(true);


        myBackgroundImage = new ImageIcon(myBackgroundPath);
        myButton = new JButton();
        myButton.setIcon(myBackgroundImage);
        myButton.setBounds(0, 0, myFrameWidth, myFrameHeight);
        myButton.setText("PRESS ANY KEY TO START");
        myButton.setVerticalTextPosition(JButton.NORTH);
        myButton.addActionListener(theAction -> {
            new SaveAndLoad();
            myFrame.dispose();
        });
        myFrame.add(myButton);


    }
    public static void main(String[] args) {
        Welcome welcome = new Welcome();
    }

}
