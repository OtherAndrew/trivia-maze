package view;

import model.Maze;
import model.mazecomponents.Direction;
import model.mazecomponents.State;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.util.StringJoiner;

import static javax.swing.JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
import static model.Maze.*;
import static model.mazecomponents.Direction.*;
import static model.mazecomponents.Door.*;
import static model.mazecomponents.Room.*;
import static model.mazecomponents.State.*;

public class Game {
    // https://www.w3schools.com/html/tryit.asp?filename=tryhtml_color_names
    public final static EmptyBorder PADDING = new EmptyBorder(10, 10, 10, 10);
    public final static EmptyBorder VERTICAL_PADDING = new EmptyBorder(10, 0,
            10, 0);
    public static final Color NON_TRAVERSABLE_COLOR = Color.BLACK;
    public static final Color GOAL_COLOR = Color.decode("#3cb371");
    public static final Color START_COLOR = Color.decode("#1e90ff");
    public static final Color LOCKED_COLOR = Color.decode("#ff6347");
    public static final Color CLOSED_COLOR = Color.decode("#6a5acd");
    public static final Color UNDISCOVERED_COLOR = Color.DARK_GRAY;
    public static final Color TRAVERSABLE_COLOR = Color.LIGHT_GRAY;
    public static final Color UNVISITED_COLOR = Color.GRAY;
    public static final String[] DIRECTION_TEXT = {"Up", "Left", "Right", "Down"};

    int r = new Random().nextInt(3) + 4;
    final Maze maze = new Maze(r, r);

    private JFrame myFrame;
    private JPanel myMapDisplay, mySidebar, myQuestionAnswerPanel, myAnswerPanel, myDirectionPanel;
    private JButton myNorthButton, myWestButton, myEastButton, mySouthButton;

    private JTextArea myQuestionArea;
    private JRadioButton[] myAnswerButtons;
    private ButtonGroup myAnswerButtonsGroup;
    private final String myTitle = "Trivia Maze", myWindowIconPath = "assets" +
            "\\Landing_page_01.png";
    private final Dimension myPreferredSize = new Dimension(720, 600);

    public Game() {
        myFrame = drawFrame(myTitle, myPreferredSize);

        // Left
        myMapDisplay = drawMapDisplay(maze.toCharArray());
        myFrame.add(myMapDisplay, BorderLayout.CENTER);

        // Right
        mySidebar = new JPanel(new BorderLayout());
        myQuestionArea = drawQuestionArea();
        myQuestionAnswerPanel = drawQAPanel(myQuestionArea);
        mySidebar.add(myQuestionAnswerPanel, BorderLayout.CENTER);
        myDirectionPanel = drawDirectionControls();
        mySidebar.add(myDirectionPanel, BorderLayout.SOUTH);
        mySidebar.setBorder(PADDING);
        myFrame.add(mySidebar, BorderLayout.EAST);

        final updateGui north = new updateGui(NORTH);
        final updateGui east = new updateGui(EAST);
        final updateGui south = new updateGui(SOUTH);
        final updateGui west = new updateGui(WEST);
        addButtonActionListeners(north, east, south, west);
        addKeyboardBindings(north, east, south, west);
        myFrame.setVisible(true);
    }

    private JFrame drawFrame(final String theTitle,
                             final Dimension thePreferredSize) {
        final JFrame frame = new JFrame(theTitle);
        frame.setLayout(new BorderLayout());
        frame.setSize(thePreferredSize);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

    private class updateGui extends AbstractAction {

        private final Direction myDirection;

        private updateGui(final Direction theDirection) {
            myDirection = theDirection;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            doMove(myDirection);
            myFrame.revalidate();
            myFrame.repaint();
        }
    }

    private void addButtonActionListeners(final updateGui... theDirections) {
        myNorthButton.addActionListener(theDirections[0]);
        myEastButton.addActionListener(theDirections[1]);
        mySouthButton.addActionListener(theDirections[2]);
        myWestButton.addActionListener(theDirections[3]);
    }

    private void disableButtonActionListeners() {
        myNorthButton.setEnabled(false);
        myEastButton.setEnabled(false);
        mySouthButton.setEnabled(false);
        myWestButton.setEnabled(false);
    }

    private void addKeyboardBindings(final updateGui... theDirections) {
        final JRootPane pane = myFrame.getRootPane();
        final InputMap inputMap = pane.getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke("W"), "moveNorth");
        inputMap.put(KeyStroke.getKeyStroke("D"), "moveEast");
        inputMap.put(KeyStroke.getKeyStroke("S"), "moveSouth");
        inputMap.put(KeyStroke.getKeyStroke("A"), "moveWest");
        final ActionMap actionMap = pane.getActionMap();
        actionMap.put("moveNorth", theDirections[0]);
        actionMap.put("moveEast", theDirections[1]);
        actionMap.put("moveSouth", theDirections[2]);
        actionMap.put("moveWest", theDirections[3]);
    }

    private void disableKeyboardBindings() {
        myFrame.getRootPane().getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).clear();
    }

    private void doMove(final Direction theDirection) {
        maze.getPlayerLocation().setDoorState(theDirection, State.OPEN);
        maze.attemptMove(theDirection);
        myFrame.remove(myMapDisplay);
        if (maze.atGoal()) {
            final StringJoiner sj = new StringJoiner("\n");
            sj.add("YOU WON!");
            sj.add("");
            sj.add("Rooms visited: " + maze.getRoomVisitedNum());
            sj.add("Rooms not visited: " + maze.getRoomNotVisitedNum());
            sj.add("");
            sj.add("Opened doors: " + maze.getDoorStateNum(OPEN));
            sj.add("Closed doors: " + maze.getDoorStateNum(CLOSED));
            sj.add("Locked doors: " + maze.getDoorStateNum(LOCKED));
            sj.add("Undiscovered doors: " + maze.getDoorStateNum(UNDISCOVERED));
            myQuestionArea.setText(sj.toString());
            myMapDisplay = drawMapDisplay(maze.toCharArray(), true);
            disableButtonActionListeners();
            disableKeyboardBindings();
        } else {
            myMapDisplay = drawMapDisplay(maze.toCharArray());
        }
        myFrame.add(myMapDisplay, BorderLayout.CENTER);
    }

    private JPanel drawQAPanel(final JTextArea theQuestionArea) {
        // Top
        final JPanel qaPanel = new JPanel(new BorderLayout());

        // TODO Get the question corresponding to selected door and display it
        drawQuestionArea();

        qaPanel.add(theQuestionArea, BorderLayout.CENTER);

        // Bottom
        qaPanel.add(drawAnswerPanel(), BorderLayout.SOUTH);
        qaPanel.setBorder(VERTICAL_PADDING);
        return qaPanel;
    }

    private JTextArea drawQuestionArea() {
        final JTextArea questionArea = new JTextArea();
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setEditable(false);
        questionArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        // SAMPLE TEXT
        questionArea.setText("Where does the majority of the world's apples " +
                "come from?");
        return questionArea;
    }

    private JPanel drawAnswerPanel() {
        // SAMPLE ANSWER ARRAY
        final String[] answerArray = {"Wisconsin", "Washington", "Canada",
                "California"};

        int numberOfAnswers = answerArray.length;
        // TODO get the number of answers from controller, do text input or
        //  radio buttons
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
        final JPanel directionControls = new JPanel(new GridLayout(3, 3));
        myNorthButton = new JButton(DIRECTION_TEXT[0]);
        myWestButton = new JButton(DIRECTION_TEXT[1]);
        myEastButton = new JButton(DIRECTION_TEXT[2]);
        mySouthButton = new JButton(DIRECTION_TEXT[3]);

        directionControls.add(new JPanel());
        directionControls.add(myNorthButton);
        directionControls.add(new JPanel());
        directionControls.add(myWestButton);
        directionControls.add(new JPanel());
        directionControls.add(myEastButton);
        directionControls.add(new JPanel());
        directionControls.add(mySouthButton);
        directionControls.add(new JPanel());

        directionControls.setBorder(VERTICAL_PADDING);
        return directionControls;
    }

    /**
     * Draws a map display from the character array representation of one or
     * more rooms.
     *
     * @param theCharArray  a character array representing one or more rooms
     *                      in the maze.
     * @param theOmniscient if the output should display an omniscient view.
     * @return a JPanel that displays the character array as a series of tiles.
     */
    private JPanel drawMapDisplay(final char[][] theCharArray,
                                  final boolean theOmniscient) {
        final JPanel mapDisplay =
                new JPanel(new GridLayout(theCharArray.length,
                        theCharArray[0].length));
        for (char[] row : theCharArray) {
            for (char space : row) {
                mapDisplay.add(buildTile(space, theOmniscient));
            }
        }
        mapDisplay.setBorder(PADDING);
        return mapDisplay;
    }



    /**
     * Draws an omniscient map display with no preferred tile size.
     *
     * @param theCharArray a character array representing one or more rooms
     *                     in the maze.
     * @return a JPanel that displays the character array as a series of tiles.
     */
    private JPanel drawMapDisplay(final char[][] theCharArray) {
        return drawMapDisplay(theCharArray,false);
    }

    /**
     * Returns a tile based on the given parameters.
     *
     * @param theChar       the character representation of the tile.
     * @param theOmniscient if an omniscient view is desired.
     * @return a maze tile.
     */
    // TODO: decide on appearance for tiles
    private JComponent buildTile(final char theChar, final boolean theOmniscient) {
        final JComponent tile = new JPanel();
        switch (theChar) {
            case Maze.PLAYER_SYMBOL -> {
                final JLabel player = new JLabel(String.valueOf(PLAYER_SYMBOL));
                player.setHorizontalAlignment(SwingConstants.CENTER);
                player.setForeground(Color.BLACK);
                if (maze.atGoal()) tile.setBackground(GOAL_COLOR);
                else if (maze.atStart()) tile.setBackground(START_COLOR);
                else tile.setBackground(TRAVERSABLE_COLOR);
                tile.setLayout(new BorderLayout());
                tile.add(player, BorderLayout.CENTER);
            }
            case UNDISCOVERED_SYMBOL, UNVISITED_SYMBOL -> {
                if (theOmniscient) tile.setBackground(UNDISCOVERED_COLOR);
                else tile.setBackground(NON_TRAVERSABLE_COLOR);
            }
            case OPEN_SYMBOL, VISITED_SYMBOL ->
                    tile.setBackground(TRAVERSABLE_COLOR);
            case GOAL_SYMBOL -> {
                if (theOmniscient) tile.setBackground(GOAL_COLOR);
                else tile.setBackground(NON_TRAVERSABLE_COLOR);
            }
            case LOCKED_SYMBOL -> {
                if (theOmniscient) tile.setBackground(LOCKED_COLOR);
                else tile.setBackground(NON_TRAVERSABLE_COLOR);
            }
            case WALL_SYMBOL -> tile.setBackground(NON_TRAVERSABLE_COLOR);
            case START_SYMBOL -> tile.setBackground(START_COLOR);
            case CLOSED_SYMBOL -> tile.setBackground(CLOSED_COLOR);
        }
        return tile;
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}

