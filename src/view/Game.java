package view;

import model.Maze;
import model.mazecomponents.Door;
import model.mazecomponents.Room;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Game {

    private JFrame myFrame;
    private JPanel myMapPanel, myDirectionPanel, myQuestionAnswerPanel, myAnswerPanel, myMapDisplay, myQuestionPanel;
    private JButton myRoomButton, myMapButton, myEastButton, myWestButton, myNorthButton, mySouthButton;

    private JTextArea myQuestion;
    private JRadioButton[] myAnswerButtons;
    private ButtonGroup myAnswerButtonsGroup;
    private final String myTitle = "Trivia Maze", myWindowIconPath = "assets\\Landing_page_01.png";
    private final Dimension myPreferredSize = new Dimension(720,600);

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

        JPanel controls = new JPanel(new BorderLayout());

        // Left half

        // Top Left
        // TODO: map display
//        final String mazePlaceholder = Files.readString(Path.of("src/view/assets/mazePlaceholder.txt"));
        final Maze maze = new Maze(5, 5);
        System.out.println(maze);
        final char[][] mazeChar = maze.toCharArray();

        myMapDisplay = new JPanel(new GridLayout(mazeChar.length, mazeChar[0].length));

        // read string to 2d array of char
        for (char[] row : maze.toCharArray()) {
            for (char space : row) {
                JComponent tile = new JPanel();
                tile.setMaximumSize(new Dimension(50, 50));
                switch (space) {
                    case Maze.WALL -> tile.setBackground(Color.BLACK);
                    case Maze.PLAYER_SYMBOL -> tile.setBackground(Color.CYAN);
                    case Maze.GOAL_SYMBOL -> tile.setBackground(Color.GREEN);
                    case Door.OPEN_SYMBOL -> tile.setBackground(Color.WHITE);
                    case Door.CLOSED_SYMBOL -> tile.setBackground(Color.GRAY);
                    case Door.LOCKED_SYMBOL -> tile.setBackground(Color.DARK_GRAY);
                    case Room.UNVISITED_SYMBOL -> tile.setBackground(Color.LIGHT_GRAY);
                    case Room.VISITED_SYMBOL -> tile.setBackground(Color.BLUE);
                }
                myMapDisplay.add(tile);
            }
        }


        myFrame.add(myMapDisplay, BorderLayout.CENTER);


        // Bottom Left
        myDirectionPanel = new JPanel(new GridLayout(3, 3));
        myEastButton = new JButton("Right");
        myNorthButton = new JButton("Up");
        mySouthButton = new JButton("Down");
        myWestButton = new JButton("Left");
        // TODO Add action listeners to buttons

        myDirectionPanel.add(new JPanel());
        myDirectionPanel.add(myNorthButton);
        myDirectionPanel.add(new JPanel());
        myDirectionPanel.add(myWestButton);
        myDirectionPanel.add(new JPanel());
        myDirectionPanel.add(myEastButton);
        myDirectionPanel.add(new JPanel());
        myDirectionPanel.add(mySouthButton);
        myDirectionPanel.add(new JPanel());

        controls.add(myDirectionPanel, BorderLayout.SOUTH);



//        myFrame.add(myMapPanel);

        // Right Half
        // Top Right
        myQuestionAnswerPanel = new JPanel(new BorderLayout());

        myQuestion = new JTextArea();
        myQuestion.setLineWrap(true);
        myQuestion.setText("Cat's Paw heels, manufactured by Biltrite and first available in 1904, are instantly recognizable on vintage footwear due to their bright white no-slip rubber dots and asymmetrical semi-circles along the edges of the heels creating the Cat's Paw shape. Even more recognizable is this iconic American brand's bright red packaging for the heels that featured a black cat on the side.");
        // TODO Get the question corresponding to selected door and display it

        myQuestionAnswerPanel.add(myQuestion, BorderLayout.CENTER);

        // Bottom Right
        final String[] answerArray = {"Augustus Ludlow", "Shinki Hikaku", "John Lofgren", "Wabash"};

        int numberOfAnswers = answerArray.length; // TODO get the number of answers from controller
        myAnswerPanel = new JPanel(new GridLayout(numberOfAnswers + 2, 1));
        myAnswerButtons = new JRadioButton[numberOfAnswers];
        myAnswerButtonsGroup = new ButtonGroup();
        for (int i = 0; i < numberOfAnswers; i++) {
            myAnswerButtons[i] = new JRadioButton(answerArray[i]);
            myAnswerButtonsGroup.add(myAnswerButtons[i]);
        }
        for (int i = 0; i < numberOfAnswers; i++) {
            myAnswerPanel.add(myAnswerButtons[i]);
        }
        myAnswerPanel.add(new JButton("Submit"));
        JPanel buffer = new JPanel();
        myAnswerPanel.add(buffer);
        myQuestionAnswerPanel.add(myAnswerPanel, BorderLayout.SOUTH);


        controls.add(myQuestionAnswerPanel, BorderLayout.CENTER);
        myFrame.add(controls, BorderLayout.EAST);



        myFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}

