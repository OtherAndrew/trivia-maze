package view;

import javax.swing.*;
import java.awt.*;

import static view.AppTheme.*;

public class LoadOrStartNewGame {

    private JFrame myFrame;
    private JPanel myNorthPanel, myCenterEastPanel, myCenterWestPanel, myCenterCenterPanel;
    private JButton myLoadGameBtn, myNewGameBtn, myQuitBtn;
    private final int myFrameWidth = 500, myFrameHeight = 500;
    private final String myBackgroundPath = "assets\\Landing_Page_01.png", myWindowIconPath = "assets\\App_Icon.png";

    public LoadOrStartNewGame() {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        myFrame = new JFrame();
        myFrame.setLayout(new BorderLayout());
        myFrame.setBackground(MID_GREY);
        myFrame.setTitle("Trivia Maze");
        // TODO set background to be "Landing_Page_01.png"
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myFrame.setResizable(false);
        myFrame.setSize(myFrameWidth, myFrameHeight);

        myNewGameBtn = buildButton("New Game");
        myLoadGameBtn = buildButton("Load Game");
        myQuitBtn = buildButton("Quit");

        myQuitBtn.addActionListener(theAction -> {
            System.exit(1);
        });

        myLoadGameBtn.addActionListener(theAction -> {
            new LoadGame();
            myFrame.dispose();
        });

        myNewGameBtn.addActionListener(theAction -> {
            new Game();
            myFrame.dispose();
        });

        myNorthPanel = new JPanel();
        myCenterEastPanel = new JPanel();
        myCenterWestPanel = new JPanel();
        myCenterCenterPanel = new JPanel();

        myCenterEastPanel.add(myNewGameBtn, BorderLayout.CENTER);
        myCenterCenterPanel.add(myLoadGameBtn, BorderLayout.CENTER);
        myCenterWestPanel.add(myQuitBtn, BorderLayout.CENTER);

        myNorthPanel.add(myCenterEastPanel);
        myNorthPanel.add(myCenterCenterPanel);
        myNorthPanel.add(myCenterWestPanel);

        myFrame.add(myNorthPanel, BorderLayout.SOUTH);
//        JPanel center = new Game().drawMapDisplay(new Maze(5, 5).toCharArray(), true);
//        myFrame.add(
//                center
//                , BorderLayout.CENTER
//        );

        myFrame.setVisible(true);
    }

    public static void main(String[] args) {
        LoadOrStartNewGame losng = new LoadOrStartNewGame();
    }
}
