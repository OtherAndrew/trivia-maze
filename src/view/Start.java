package view;

import javax.swing.*;
import java.awt.*;

import static view.AppTheme.*;
import static view.MazeDisplayBuilder.buildDummyMapDisplay;
import static view.MazeDisplayBuilder.buildMapDisplay;

public class Start {

    private JFrame myFrame;
    private JPanel myMenuBar;
    private JButton myLoadGameBtn, myNewGameBtn, myQuitBtn;
    private final String myBackgroundPath = "assets\\Landing_Page_01.png", myWindowIconPath = "assets\\App_Icon.png";

    public Start() {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        myFrame = buildFrame();

        myNewGameBtn = buildButton("New Game");
        myLoadGameBtn = buildButton("Load Game");
        myQuitBtn = buildButton("Quit");

        myNewGameBtn.addActionListener(theAction -> {
            new Difficulty();
            myFrame.dispose();
        });

        myLoadGameBtn.addActionListener(theAction -> {
            new LoadGame();
            myFrame.dispose();
        });

        myQuitBtn.addActionListener(theAction -> {
            System.exit(1);
        });

        myMenuBar = buildMenubar(new JButton[]{
                myNewGameBtn, myLoadGameBtn, myQuitBtn});

        myFrame.add(myMenuBar, BorderLayout.NORTH);
        myFrame.add(buildDummyMapDisplay(6), BorderLayout.CENTER);

        myFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Start losng = new Start();
    }
}
