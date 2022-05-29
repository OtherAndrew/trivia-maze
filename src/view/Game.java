package view;

import model.Maze;
import model.mazecomponents.Door;
import model.mazecomponents.Room;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Random;

import static model.Maze.*;
import static model.mazecomponents.Room.*;
import static model.mazecomponents.Door.*;



public class Game {

    final static EmptyBorder PADDING = new EmptyBorder(10, 10, 10, 10);

    private JFrame myFrame;
    private JPanel myMapPanel, myDirectionPanel, myQuestionAnswerPanel, myAnswerPanel, myMapDisplay, myQuestionPanel, myOuterDirectionPanel;
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

        // Left half
//        final String mazePlaceholder = Files.readString(Path.of("src/view/assets/mazePlaceholder.txt"));
        final Random r = new Random();
        final Maze maze = new Maze(r.nextInt(6) + 3, r.nextInt(6) + 3);
//        maze.setAllDoors(State.OPEN);
        System.out.println(maze);
        myFrame.add(drawMapDisplay(maze.playerRoomToCharArray()), BorderLayout.CENTER);
//        myFrame.add(drawMapDisplay(maze.toCharArray()), BorderLayout.CENTER);

        // Right
        JPanel sidebar = new JPanel(new BorderLayout());
        // MINIMAP
        // TODO: option select for minimap?
        sidebar.add(drawMapDisplay(maze.toCharArray(), 10), BorderLayout.NORTH);
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
        myQuestion.setWrapStyleWord(true);
        myQuestion.setEditable(false);
        myQuestion.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        // SAMPLE TEXT
        myQuestion.setText("Where does the majority of the world's apples come from?");

        myQuestionAnswerPanel.add(myQuestion, BorderLayout.CENTER);

        // Bottom

        myQuestionAnswerPanel.add(drawAnswerPanel(), BorderLayout.SOUTH);
        myQuestionAnswerPanel.setBorder(PADDING);
        return myQuestionAnswerPanel;
    }

    private JPanel drawAnswerPanel() {
        // SAMPLE ANSWER ARRAY
        final String[] answerArray = {"Wisconsin", "Washington", "Canada", "California"};

        int numberOfAnswers = answerArray.length;
        // TODO get the number of answers from controller, do text input or radio buttons
        myAnswerPanel = new JPanel(new GridLayout(numberOfAnswers + 1, 1));
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

        myDirectionPanel.setBorder(PADDING);
        return myDirectionPanel;
    }

    /**
     * Draws a JPanel based on the character array input.
     *
     * @param theCharArray a character array representing one or more rooms
     *                     in the maze.
     * @param theTileSize the preferred tile size for the output.
     * @return a JPanel that displays the character array as a series of tiles.
     */
    private JPanel drawMapDisplay(final char[][] theCharArray, final int theTileSize) {
        myMapDisplay = new JPanel(
                new GridLayout(theCharArray.length, theCharArray[0].length));
        for (char[] row : theCharArray) {
            for (char space : row) {
                // TODO: decide on appearance for tiles
                final JComponent tile = new JPanel();
                if (theTileSize > 0) {
                    tile.setPreferredSize(new Dimension(theTileSize, theTileSize));
                }
                switch (space) {
                    case Maze.PLAYER_SYMBOL -> {
                        final JLabel player = new JLabel("YOU");
                        player.setHorizontalAlignment(SwingConstants.CENTER);
                        player.setForeground(Color.BLACK);
                        tile.setBackground(Color.LIGHT_GRAY);
                        tile.setLayout(new BorderLayout());
                        tile.add(player, BorderLayout.CENTER);
                    }
                    case WALL_SYMBOL, LOCKED_SYMBOL -> tile.setBackground(Color.BLACK);
                    case GOAL_SYMBOL -> tile.setBackground(Color.GREEN);
                    case START_SYMBOL -> tile.setBackground(Color.RED);
                    case OPEN_SYMBOL, VISITED_SYMBOL -> tile.setBackground(Color.LIGHT_GRAY);
                    case CLOSED_SYMBOL -> tile.setBackground(Color.DARK_GRAY);
                    case UNVISITED_SYMBOL -> tile.setBackground(Color.GRAY);
                }
                myMapDisplay.add(tile);
            }
        }
        myMapDisplay.setBorder(PADDING);
        return myMapDisplay;
    }

    private JPanel drawMapDisplay(final char[][] theCharArray) {
        return drawMapDisplay(theCharArray, 0);
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}

