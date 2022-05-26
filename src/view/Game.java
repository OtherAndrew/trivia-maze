package view;

import javax.swing.*;
import java.awt.*;

public class Game {

    private JFrame myFrame;
    private JPanel myMapPanel, myDirectionPanel, myQuestionPanel, myAnswerPanle;
    private JButton myRoomButton, myMapButton, myEastButton, myWestButton, myNorthButton, mySouthButton;
    private final String myTitle = "Trivia Maze", myWindowIconPath = "assets\\Landing_page_01.png";
    private final Dimension myPreferredSize = new Dimension(1000,1000);

    public Game() {
        myFrame = new JFrame(myTitle);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        myFrame.setSize(myPreferredSize);
        myFrame.setLocationRelativeTo(null);


        // Top left
        myMapPanel = new JPanel();
        // TODO Show current room and highlight doors
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 250;
        gbc.gridy = 0;
        gbc.gridx = 0;
        myFrame.add(myMapPanel);

        // Lower left
        myDirectionPanel = new JPanel(new GridLayout(1, 4));
        myEastButton = new JButton("East");
        myNorthButton = new JButton("North");
        mySouthButton = new JButton("South");
        myWestButton = new JButton("West");
        // TODO Add action listeners to buttons
        myDirectionPanel.add(myEastButton);
        myDirectionPanel.add(myNorthButton);
        myDirectionPanel.add(mySouthButton);
        myDirectionPanel.add(myWestButton);
        gbc.gridy = 1;
        gbc.gridx = 0;
        myFrame.add(myDirectionPanel);

        // Top right

        // Lower right



        myFrame.setVisible(true);




    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}

