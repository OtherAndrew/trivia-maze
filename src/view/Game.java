package view;

import model.Maze;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Random;

import static model.Maze.*;
import static model.mazecomponents.Room.*;
import static model.mazecomponents.Door.*;



public class Game {

    final static EmptyBorder PADDING = new EmptyBorder(10, 10, 10, 10);
    public static final Color NON_TRAVERSABLE_COLOR = Color.BLACK;
    public static final Color GOAL_COLOR = Color.GREEN;
    public static final Color START_COLOR = new Color(128, 0, 0);
    public static final Color TRAVERSABLE_COLOR = Color.LIGHT_GRAY;
    public static final Color CLOSED_COLOR = Color.DARK_GRAY;
    public static final Color UNVISITED_COLOR = Color.GRAY;
    public static final String[] CONTROL_TEXT = {"UP", "LEFT", "RIGHT", "DOWN"};


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
//        final Maze maze = new Maze(r.nextInt(7) + 3, r.nextInt(7) + 3);
        final Maze maze = new Maze(10, 10);
//        maze.setAllDoors(State.OPEN);
        System.out.println(maze);
        myFrame.add(drawMapDisplay(maze.playerRoomToCharArray()), BorderLayout.CENTER);
//        myFrame.add(drawMapDisplay(maze.toCharArray()), BorderLayout.CENTER);

        // Right
        JPanel sidebar = new JPanel(new BorderLayout());
        // MINIMAP
        // TODO: omniscence select for minimap?
        sidebar.add(drawMapDisplay(maze.toCharArray(), 10, false), BorderLayout.NORTH);
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
     * Draws a map display from the character array representation of one or
     * more rooms.
     *
     * @param theCharArray a character array representing one or more rooms
     *                     in the maze.
     * @param theTileSize the preferred tile size for the output. If <= 0 then
     *                    no preferred sizing will be applied.
     * @param theOmniscient if the output should display an omniscient view.
     * @return a JPanel that displays the character array as a series of tiles.
     */
    private JPanel drawMapDisplay(final char[][] theCharArray, final int theTileSize,
                                  final boolean theOmniscient) {
        myMapDisplay = new JPanel(
                new GridLayout(theCharArray.length, theCharArray[0].length));
        for (char[] row : theCharArray) {
            for (char space : row) {
                myMapDisplay.add(buildTile(space, theTileSize, theOmniscient));
            }
        }
        myMapDisplay.setBorder(PADDING);
        return myMapDisplay;
    }

    /**
     * Draws a map display from the character array representation of one or
     * more rooms with no preferred tile size.
     *
     * @param theCharArray a character array representing one or more rooms
     *                     in the maze.
     * @param theOmniscient if the output should display an omniscient view.
     * @return a JPanel that displays the character array as a series of tiles.
     */
    private JPanel drawMapDisplay(final char[][] theCharArray, final boolean theOmniscient) {
        return drawMapDisplay(theCharArray, 0, theOmniscient);
    }

    /**
     * Draws an omniscient map display with a preferred tile size.
     *
     * @param theCharArray a character array representing one or more rooms
     *                     in the maze.
     * @param theTileSize the preferred tile size for the output. If <= 0 then
     *                    no preferred sizing will be applied.
     * @return
     */
    private JPanel drawMapDisplay(final char[][] theCharArray, final int theTileSize) {
        return drawMapDisplay(theCharArray, theTileSize, true);
    }

    /**
     * Draws an omniscient map display with no preferred tile size.
     *
     * @param theCharArray a character array representing one or more rooms
     *                     in the maze.
     * @return a JPanel that displays the character array as a series of tiles.
     */
    private JPanel drawMapDisplay(final char[][] theCharArray) {
        return drawMapDisplay(theCharArray, 0, true);
    }

    /**
     * Returns a tile based on the given parameters.
     *
     * @param theChar the character representation of the tile.
     * @param theTileSize the preferred size of the tile. If <= 0 then
     *                    no preferred sizing will be applied.
     * @param theOmniscient if an omniscient view is desired.
     * @return a maze tile.
     */
    // TODO: decide on appearance for tiles
    private JComponent buildTile(final char theChar, final int theTileSize,
                                 final boolean theOmniscient) {
        final JComponent tile = new JPanel();
        if (theTileSize > 0) {
            tile.setPreferredSize(new Dimension(theTileSize, theTileSize));
        }
        switch (theChar) {
            case Maze.PLAYER_SYMBOL -> {
                final JLabel player = new JLabel("YOU");
                player.setHorizontalAlignment(SwingConstants.CENTER);
                player.setForeground(Color.BLACK);
                tile.setBackground(TRAVERSABLE_COLOR);
                tile.setLayout(new BorderLayout());
                tile.add(player, BorderLayout.CENTER);
            }
            case UNDISCOVERED_SYMBOL -> {
                if (theOmniscient) tile.setBackground(CLOSED_COLOR);
                else tile.setBackground(NON_TRAVERSABLE_COLOR);
            }
            case UNVISITED_SYMBOL -> {
                if (theOmniscient) tile.setBackground(UNVISITED_COLOR);
                else tile.setBackground(NON_TRAVERSABLE_COLOR);
            }
            case WALL_SYMBOL, LOCKED_SYMBOL -> tile.setBackground(NON_TRAVERSABLE_COLOR);
            case OPEN_SYMBOL, VISITED_SYMBOL -> tile.setBackground(TRAVERSABLE_COLOR);
            case START_SYMBOL -> tile.setBackground(START_COLOR);
            case CLOSED_SYMBOL -> tile.setBackground(CLOSED_COLOR);
            case GOAL_SYMBOL -> tile.setBackground(GOAL_COLOR);
        }
        return tile;
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}

