package view;

import model.Maze;
import model.mazecomponents.Direction;

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
import static view.AppTheme.*;

public class Game {

    public final static EmptyBorder DIRECTION_PADDING = new EmptyBorder(17, 0, 7, 0);
    public final static EmptyBorder PADDING = new EmptyBorder(10, 10, 10, 10);
    public final static EmptyBorder SIDEBAR_PADDING = new EmptyBorder(0, 7, 0, 0);
    public final static EmptyBorder ANSWER_PADDING = new EmptyBorder(7, 0, 0, 0);

    public static final String[] DIRECTION_TEXT = {"Up", "Left", "Right", "Down"};

    public static final String SAMPLE_QUERY = "Where does the majority of the world's apples come from?";
    public static final String[] SAMPLE_ANSWERS = {"Wisconsin", "Washington", "Canada", "California"};

    int r = new Random().nextInt(6) + 4;
    final Maze maze = new Maze(r, r);

    private JFrame myFrame;
    private JPanel myMapDisplay, mySidebar, myQAPanel, myQuestionPanel, myAnswerPanel, myAnswerButtonPanel, myDirectionPanel;
    private JButton myNorthButton, myWestButton, myEastButton, mySouthButton, mySubmitButton;

    private JTextArea myQuestionArea;
    private JRadioButton[] myAnswerButtons;
    private ButtonGroup myAnswerButtonsGroup;
    private final String myTitle = "Trivia Maze", myWindowIconPath = "assets" +
            "\\Landing_page_01.png";
    private final Dimension myPreferredSize = new Dimension(720, 600);

    public Game() {
        System.setProperty("awt.useSystemAAFontSettings","on");
        myFrame = drawFrame();
        // Left
        myMapDisplay = drawMapDisplay(maze.toCharArray());
        myFrame.add(myMapDisplay, BorderLayout.CENTER);
        // Right
        mySidebar = new JPanel(new BorderLayout());
        mySidebar.add(drawQAPanel(SAMPLE_QUERY, SAMPLE_ANSWERS), BorderLayout.CENTER);
        mySidebar.add(drawDirectionControls(), BorderLayout.SOUTH);
        mySidebar.setBorder(SIDEBAR_PADDING);
        mySidebar.setBackground(MID_GREY);
        myFrame.add(mySidebar, BorderLayout.EAST);

        final updateGui north = new updateGui(NORTH);
        final updateGui east = new updateGui(EAST);
        final updateGui south = new updateGui(SOUTH);
        final updateGui west = new updateGui(WEST);
        addButtonActionListeners(north, east, south, west);
        addKeyboardBindings(north, east, south, west);
        myFrame.setVisible(true);
    }

    /**
     * Draws the window frame.
     *
     * @return the window frame.
     */
    private JFrame drawFrame() {
        myFrame = new JFrame(myTitle);
        myFrame.setLayout(new BorderLayout());
        myFrame.setSize(myPreferredSize);
        myFrame.getRootPane().setBorder(PADDING);
        myFrame.setBackground(MID_GREY);
        myFrame.setResizable(false);
        myFrame.setLocationRelativeTo(null);
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return myFrame;
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
        // TODO: remove line below for final revision
        maze.getPlayerLocation().setDoorState(theDirection, OPEN);
        maze.attemptMove(theDirection);
        myFrame.remove(myMapDisplay);
        if (maze.atGoal()) {
            final StringJoiner sj = new StringJoiner("\n");
            sj.add("YOU WON!");
            sj.add("");
            sj.add("Rooms visited: " + maze.getRoomVisitedNum());
            sj.add("Rooms not visited: " + maze.getRoomVisitedNum(false));
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
            myMapDisplay = drawMapDisplay(maze.toCharArray(), false);
        }
        myFrame.add(myMapDisplay, BorderLayout.CENTER);
    }

    /**
     * Draws the question/answer panel from a query and an array of answers.
     *
     * @param theQueryText the query.
     * @param theAnswerArray a set of answers.
     * @return the question/answer panel.
     */
    private JPanel drawQAPanel(final String theQueryText, final String[] theAnswerArray) {
        myQAPanel = new JPanel(new BorderLayout());
        myQAPanel.add(drawQuestionArea(theQueryText), BorderLayout.CENTER);
        myQAPanel.add(drawAnswerPanel(theAnswerArray), BorderLayout.SOUTH);
        myQAPanel.setBackground(MID_GREY);
        return myQAPanel;
    }

    /**
     * Draws the question area with the input text.
     *
     * @param theQueryText the text the question area should contain.
     * @return the question area.
     */
    private JPanel drawQuestionArea(final String theQueryText) {
        myQuestionPanel = new JPanel(new BorderLayout());
        myQuestionPanel.setBackground(DARK_GREY);
        myQuestionPanel.setBorder(PADDING);

        myQuestionArea = new JTextArea();
        myQuestionArea.setLineWrap(true);
        myQuestionArea.setWrapStyleWord(true);
        myQuestionArea.setEditable(false);
        myQuestionArea.setFont(TEXT_FONT);
        myQuestionArea.setText(theQueryText);
        myQuestionArea.setBackground(DARK_GREY);
        myQuestionArea.setForeground(WHITE);
        myQuestionPanel.add(myQuestionArea, BorderLayout.CENTER);
        return myQuestionPanel;
    }

    /**
     * Draws blank question area.
     *
     * @return the question area.
     */
    private JPanel drawQuestionArea() {
        return drawQuestionArea("");
    }

    // TODO do text input or radio buttons based on input
    private JPanel drawAnswerPanel(final String[] theAnswerArray) {
        int numberOfAnswers = theAnswerArray.length;
        myAnswerPanel = new JPanel(new BorderLayout());
        myAnswerPanel.setBorder(ANSWER_PADDING);
        myAnswerPanel.setBackground(MID_GREY);
        myAnswerButtonPanel = new JPanel(new GridLayout(numberOfAnswers, 1));
        myAnswerButtonPanel.setBorder(PADDING);
        myAnswerButtonPanel.setBackground(DARK_GREY);
        myAnswerButtons = new JRadioButton[numberOfAnswers];
        myAnswerButtonsGroup = new ButtonGroup();
        for (int i = 0; i < numberOfAnswers; i++) {
            myAnswerButtons[i] = new JRadioButton(theAnswerArray[i]);
            myAnswerButtons[i].setBackground(DARK_GREY);
            myAnswerButtons[i].setForeground(WHITE);
            myAnswerButtons[i].setFont(BUTTON_FONT);
            myAnswerButtonsGroup.add(myAnswerButtons[i]);
            myAnswerButtonPanel.add(myAnswerButtons[i]);
        }
        mySubmitButton = new JButton("Submit");
        mySubmitButton.setBackground(LIGHT_GREY);
        mySubmitButton.setForeground(BLACK);
        mySubmitButton.setFont(BUTTON_FONT);
        myAnswerPanel.add(myAnswerButtonPanel, BorderLayout.CENTER);
        myAnswerPanel.add(mySubmitButton, BorderLayout.SOUTH);
        return myAnswerPanel;
    }

    /**
     * Draws the direction controls.
     *
     * @return the direction control panel.
     */
    private JPanel drawDirectionControls() {
        myDirectionPanel = new JPanel(new GridLayout(3, 3));
        myNorthButton = new JButton(DIRECTION_TEXT[0]);
        myWestButton = new JButton(DIRECTION_TEXT[1]);
        myEastButton = new JButton(DIRECTION_TEXT[2]);
        mySouthButton = new JButton(DIRECTION_TEXT[3]);
        final JButton[] directionButtons =
                {myNorthButton, myWestButton, myEastButton, mySouthButton};
        for (JButton button : directionButtons) {
            addBufferPanel();
            button.setForeground(BLACK);
            button.setBackground(LIGHT_GREY);
            button.setFont(BUTTON_FONT);
            myDirectionPanel.add(button);
        }
        addBufferPanel();
        myDirectionPanel.setBorder(DIRECTION_PADDING);
        myDirectionPanel.setBackground(MID_GREY);
        return myDirectionPanel;
    }

    /**
     * Adds buffer panel to direction panel.
     */
    private void addBufferPanel() {
        final JPanel buffer = new JPanel();
        buffer.setBackground(MID_GREY);
        myDirectionPanel.add(buffer);
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
        mapDisplay.setBackground(MID_GREY);
        return mapDisplay;
    }

    /**
     * Draws a map display showing the discovered rooms and doors.
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
    private JComponent buildTile(final char theChar, final boolean theOmniscient) {
        final JComponent tile = new JPanel(new BorderLayout());
        switch (theChar) {
            case Maze.PLAYER_SYMBOL -> {
                if (maze.atGoal()) tile.setBackground(GREEN);
                else if (maze.atStart()) tile.setBackground(ORANGE);
                else tile.setBackground(LIGHT_GREY);
                tile.add(drawLabel("[]"), BorderLayout.CENTER);
            }
            case UNDISCOVERED_SYMBOL, UNVISITED_SYMBOL -> {
                if (theOmniscient) tile.setBackground(MID_GREY);
                else tile.setBackground(DARK_GREY);
            }
            case GOAL_SYMBOL -> {
                if (theOmniscient) tile.setBackground(GREEN);
                else tile.setBackground(DARK_GREY);
            }
            case LOCKED_SYMBOL -> {
                tile.setBackground(RED);
                tile.add(drawLabel("X"), BorderLayout.CENTER);
            }
            case CLOSED_SYMBOL -> {
                tile.setBackground(PURPLE);
                tile.add(drawLabel("?"), BorderLayout.CENTER);
            }
            case OPEN_SYMBOL, VISITED_SYMBOL ->
                    tile.setBackground(LIGHT_GREY);
            case WALL_SYMBOL -> tile.setBackground(DARK_GREY);
            case START_SYMBOL ->  tile.setBackground(ORANGE);
        }
        return tile;
    }

    /**
     * Draws a label for a map tile with the specified text.
     *
     * @param theLabelText the text the label should display.
     * @return a label with the specified text.
     */
    private JLabel drawLabel(final String theLabelText) {
        final JLabel output = new JLabel(theLabelText);
        output.setHorizontalAlignment(SwingConstants.CENTER);
        output.setForeground(BLACK);
        output.setFont(MAP_TILE_FONT);
        return output;
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}

