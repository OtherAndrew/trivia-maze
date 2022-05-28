package view;

import model.Maze;
import model.mazecomponents.Door;
import model.mazecomponents.Room;

import javax.swing.*;
import java.awt.*;

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

        // TODO Show current room and highlight doors

        // Left half
//        final String mazePlaceholder = Files.readString(Path.of("src/view/assets/mazePlaceholder.txt"));
        myFrame.add(drawMapDisplay(), BorderLayout.CENTER);

        // Right
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.add(drawDirectionControls(), BorderLayout.SOUTH);
        sidebar.add(drawQAPanel(), BorderLayout.CENTER);
        myFrame.add(sidebar, BorderLayout.EAST);

        myFrame.setVisible(true);
    }

    private JPanel drawQAPanel() {
        // Top
        myQuestionAnswerPanel = new JPanel(new BorderLayout());

        // TODO Get the question corresponding to selected door and display it
        myQuestion = new JTextArea();
        myQuestion.setLineWrap(true);
        myQuestion.setText("Cat's Paw heels, manufactured by Biltrite and first available in 1904, are instantly recognizable on vintage footwear due to their bright white no-slip rubber dots and asymmetrical semi-circles along the edges of the heels creating the Cat's Paw shape. Even more recognizable is this iconic American brand's bright red packaging for the heels that featured a black cat on the side.");
        myQuestionAnswerPanel.add(myQuestion, BorderLayout.CENTER);

        // Bottom
        myQuestionAnswerPanel.add(drawAnswerPanel(), BorderLayout.SOUTH);
        return myQuestionAnswerPanel;
    }

    private JPanel drawAnswerPanel() {
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
        return myAnswerPanel;
    }

    private JPanel drawDirectionControls() {
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
        return myDirectionPanel;
    }

    private JPanel drawMapDisplay() {
        final Maze maze = new Maze(5, 5);
        System.out.println(maze);
        final char[][] mazeChar = maze.toCharArray();

        myMapDisplay = new JPanel(new GridLayout(mazeChar.length, mazeChar[0].length));

        // read string to 2d array of char
        for (char[] row : maze.toCharArray()) {
            for (char space : row) {
                JComponent tile = new JPanel();
                switch (space) {
                    case Maze.WALL, Door.LOCKED_SYMBOL -> tile.setBackground(Color.BLACK);
                    case Maze.PLAYER_SYMBOL -> tile.setBackground(Color.CYAN);
                    case Maze.GOAL_SYMBOL -> tile.setBackground(Color.GREEN);
                    case Door.OPEN_SYMBOL, Room.VISITED_SYMBOL -> tile.setBackground(Color.WHITE);
                    case Door.CLOSED_SYMBOL -> tile.setBackground(Color.GRAY);
                    case Room.UNVISITED_SYMBOL -> tile.setBackground(Color.LIGHT_GRAY);
                }
                myMapDisplay.add(tile);
            }
        }
        return myMapDisplay;
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}

