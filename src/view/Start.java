package view;

import model.Maze;
import model.mazecomponents.State;

import javax.swing.*;
import java.awt.*;

import static view.AppTheme.*;
import static view.MazeDisplayBuilder.drawMapDisplay;

public class Start {

    private JFrame myFrame;
    private JPanel myButtonPanel;
    private JButton myLoadGameBtn, myNewGameBtn, myQuitBtn;
    private final String myBackgroundPath = "assets\\Landing_Page_01.png", myWindowIconPath = "assets\\App_Icon.png";

    public Start() {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        myFrame = buildFrame();

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

        myButtonPanel = new JPanel(new GridLayout(1, 3));
        myButtonPanel.setBackground(MID_GREY);
        myButtonPanel.add(myNewGameBtn);
        myButtonPanel.add(myLoadGameBtn);
        myButtonPanel.add(myQuitBtn);

        myFrame.add(myButtonPanel, BorderLayout.SOUTH);
        Maze m = new Maze(6, 6);
        m.setAllDoors(State.UNDISCOVERED);
        JPanel center = drawMapDisplay(m.toCharArray(), true);
        myFrame.add(center, BorderLayout.CENTER);

        myFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Start losng = new Start();
    }
}
