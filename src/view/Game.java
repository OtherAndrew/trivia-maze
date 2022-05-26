package view;

import javax.swing.*;
import java.awt.*;

public class Game {

    private JFrame myFrame;
    private JPanel myMapPanel, myDirectionPanel, myQuestionPanel, myAnswerPanel;
    private JButton myRoomButton, myMapButton, myEastButton, myWestButton, myNorthButton, mySouthButton;
    private JLabel myQuestion;
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
        myMapPanel.setBackground(new Color(255, 44,56));
        // TODO Show current room and highlight doors

        gbc.ipady = 500;
        gbc.ipadx = myFrame.getWidth()/2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        myFrame.add(myMapPanel, gbc);
        gbc.ipady = 0;
        gbc.ipadx = 0;

        // Top right
        myQuestionPanel = new JPanel();

        myQuestion = new JLabel("The question"); // Get the question corresponding to selected door and display it
        myQuestionPanel.add(myQuestion, BorderLayout.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 0;
        myFrame.add(myQuestionPanel, gbc);

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
        gbc.gridx = 0;
        gbc.gridy = 1;
        myFrame.add(myDirectionPanel, gbc);

        // Lower right




        myFrame.setVisible(true);




    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}

