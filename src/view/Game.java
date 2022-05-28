package view;

import javax.swing.*;
import java.awt.*;

public class Game {

    private JFrame myFrame;
    private JPanel myMapPanel, myDirectionPanel, myQuestionAnswerPanel;
    private JButton myRoomButton, myMapButton, myEastButton, myWestButton, myNorthButton, mySouthButton;
    private JLabel myQuestion;
    private JRadioButton[] myAnswerButtons;
    private ButtonGroup myAnswerButtonsGroup;
    private final String myTitle = "Trivia Maze", myWindowIconPath = "assets\\Landing_page_01.png";
    private final Dimension myPreferredSize = new Dimension(1000,1000);

    public Game() {
        myFrame = new JFrame(myTitle);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(new BorderLayout());
        myFrame.setSize(myPreferredSize);
        myFrame.setLocationRelativeTo(null);

        // Center
        myMapPanel = new JPanel(new BorderLayout());
        myMapPanel.setBackground(new Color(0, 0,0));
        // TODO Show current room and highlight doors

        // Bottom
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
        myMapPanel.add(myDirectionPanel, BorderLayout.SOUTH);

        myFrame.add(myMapPanel, BorderLayout.CENTER);

        // Top Right
        myQuestionAnswerPanel = new JPanel(new GridLayout(2, 1));

        // TODO Get the question corresponding to selected door and display it
        myQuestion = new JLabel("The question");

        myQuestionAnswerPanel.add(myQuestion, BorderLayout.CENTER);

        // Bottom Right
        int numberOfAnswers = 4; // TODO get the number of answers from controller
        myAnswerButtons = new JRadioButton[numberOfAnswers];
        myAnswerButtonsGroup = new ButtonGroup();
        for (int i = 0; i < numberOfAnswers; i++) {
            myAnswerButtons[i] = new JRadioButton(Integer.toString(i));
            myAnswerButtonsGroup.add(myAnswerButtons[i]);
        }
        for (int i = 0; i < numberOfAnswers; i++) {
        myQuestionAnswerPanel.add(myAnswerButtons[i]);
        }
        myFrame.add(myQuestionAnswerPanel, BorderLayout.EAST);



        myFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}

