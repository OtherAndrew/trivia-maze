package view;

import controller.TriviaMaze;
import model.mazecomponents.Direction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.StringJoiner;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import static model.mazecomponents.Direction.*;
import static model.mazecomponents.State.*;
import static view.AppTheme.*;
import static view.MazeDisplayBuilder.buildMapDisplay;

public class Game {

    public final static EmptyBorder DIRECTION_PADDING =
            new EmptyBorder(17, 0, 7, 0);
    public final static EmptyBorder SIDEBAR_PADDING =
            new EmptyBorder(0, 7, 0, 0);
    public final static EmptyBorder ANSWER_PADDING =
            new EmptyBorder(7, 0, 0, 0);

    public static final String[] DIRECTION_TEXT = {"Up", "Left", "Right",
            "Down"};

    public static final String SAMPLE_QUERY =
            "Where does the majority of " + "the" + " world's apples come " +
                    "from?";
    public static final String[] SAMPLE_ANSWERS = {"Wisconsin", "Washington",
            "Canada", "California"};

    private boolean myMovementEnabledStatus;

    private TriviaMaze myController;
    private final JFrame myFrame;
    private final JPanel myContentPanel;
    private JPanel myGamePanel;
    private JPanel myMenuBar;
    private JPanel myMapDisplay;
    private JPanel mySidebar;
    private JPanel myQAPanel;
    private JPanel myQuestionPanel;
    private JPanel myAnswerPanel;
    private JPanel myResponsePanel;
    private JPanel myDirectionPanel;
    private JPanel myAnswerSubmissionPanel;
    private JButton myNorthButton, myWestButton, myEastButton, mySouthButton,
            mySubmitButton, myNewGameButton, mySaveButton, myMainMenuButton,
            myCancelButton;

    private JTextArea myQuestionArea;
    private JTextField myAnswerPrompt;
    private JRadioButton[] myAnswerButtons;
    private ButtonGroup myAnswerButtonsGroup;

    public Game(final TriviaMaze theController) {
        myController = theController;
        theController.registerView(this);

        System.setProperty("awt.useSystemAAFontSettings", "on");
        myFrame = buildFrame();
        myContentPanel = buildPanel();
        myFrame.add(myContentPanel);

        // CardLayout
        final CardLayout cards = new CardLayout();
        myContentPanel.setLayout(cards);

        myContentPanel.add(drawGamePanel(false), "game");
        myContentPanel.add(new Difficulty(this, cards), "difficulty");
        myContentPanel.add(new Start(this, cards), "start");

        myNewGameButton.addActionListener(theAction -> cards.show(myContentPanel, "difficulty"));

        mySaveButton.addActionListener(theAction -> FileAccessor.getInstance().saveFile());

        myMainMenuButton.addActionListener(theAction -> cards.show(myContentPanel, "start"));

        // Movement
        setMovementEnabled(true);
        final updateGui north = new updateGui(NORTH);
        final updateGui east = new updateGui(EAST);
        final updateGui south = new updateGui(SOUTH);
        final updateGui west = new updateGui(WEST);
        addButtonActionListeners(north, east, south, west);
        addKeyboardBindings(north, east, south, west);

        cards.show(myContentPanel, "start");
        myFrame.setVisible(true);
    }

    private void addButtonActionListeners(final updateGui... theDirections) {
        myNorthButton.addActionListener(theDirections[0]);
        myEastButton.addActionListener(theDirections[1]);
        mySouthButton.addActionListener(theDirections[2]);
        myWestButton.addActionListener(theDirections[3]);
    }

    private void addKeyboardBindings(final updateGui... theDirections) {
        final InputMap inputMap =
                myGamePanel.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("W"), "moveNorth");
        inputMap.put(KeyStroke.getKeyStroke("D"), "moveEast");
        inputMap.put(KeyStroke.getKeyStroke("S"), "moveSouth");
        inputMap.put(KeyStroke.getKeyStroke("A"), "moveWest");
        final ActionMap actionMap = myGamePanel.getActionMap();
        actionMap.put("moveNorth", theDirections[0]);
        actionMap.put("moveEast", theDirections[1]);
        actionMap.put("moveSouth", theDirections[2]);
        actionMap.put("moveWest", theDirections[3]);
    }

    public void setMovementEnabled(final boolean theStatus) {
        myMovementEnabledStatus = theStatus;
    }

    private void doMove(final Direction theDirection) {
        myController.move(theDirection);
        myGamePanel.remove(myMapDisplay);
        if (myController.getVictory()) {
            displayWinMessage();
            myMapDisplay =
                    MazeDisplayBuilder.buildMapDisplay(myController.getMazeCharArray(),
                            true);
            setMovementEnabled(false);
        } else {
            myMapDisplay =
                    MazeDisplayBuilder.buildMapDisplay(myController.getMazeCharArray(),
                            false);
        }
        myGamePanel.add(myMapDisplay, BorderLayout.CENTER);
    }

    private void displayWinMessage() {
        final StringJoiner sj = new StringJoiner("\n");
        sj.add("You won!");
        sj.add("");
        sj.add("Rooms visited: " + myController.getVisitCount(true));
        sj.add("Rooms not visited: " + myController.getVisitCount(false));
        sj.add("");
        sj.add("Opened doors: " + myController.getMazeDoorCount(OPEN));
        sj.add("Closed doors: " + myController.getMazeDoorCount(CLOSED));
        sj.add("Locked doors: " + myController.getMazeDoorCount(LOCKED));
        sj.add("Undiscovered doors: " + myController.getMazeDoorCount(UNDISCOVERED));
        myQuestionArea.setText(sj.toString());
    }

    JPanel drawGamePanel(final boolean theReadiness) {
        // Game
        myGamePanel = buildPanel();
        // Menubar
        myGamePanel.add(drawMenuBar(), BorderLayout.NORTH);
        // Left
        myMapDisplay = buildMapDisplay(myController.getMazeCharArray());
        myGamePanel.add(myMapDisplay, BorderLayout.CENTER);
        // Right
        mySidebar = new JPanel(new BorderLayout());
        mySidebar.add(drawQAPanel(SAMPLE_QUERY/*, SAMPLE_ANSWERS*/),
                BorderLayout.CENTER);
        mySidebar.add(drawDirectionControls(), BorderLayout.SOUTH);
        mySidebar.setBorder(SIDEBAR_PADDING);
        mySidebar.setBackground(MID_GREY);
        myGamePanel.add(mySidebar, BorderLayout.EAST);
        return myGamePanel;
    }

    private JPanel drawMenuBar() {
        myNewGameButton = buildButton("New Game");
        mySaveButton = buildButton("Save");
        myMainMenuButton = buildButton("Main Menu");
        myMenuBar = buildMenubar(myNewGameButton, mySaveButton, myMainMenuButton);
        return myMenuBar;
    }

    /**
     * Draws the question/answer panel for a multiple choice question.
     *
     * @param theQueryText   the query.
     * @param theAnswerArray a set of answers.
     * @return the question/answer panel.
     */
    private JPanel drawQAPanel(final String theQueryText,
                               final String[] theAnswerArray) {
        myQAPanel = new JPanel(new BorderLayout());
        myQAPanel.add(drawQuestionArea(theQueryText), BorderLayout.CENTER);
        myQAPanel.add(drawMultipleChoicePanel(theAnswerArray), BorderLayout.SOUTH);
        myQAPanel.setBackground(MID_GREY);
        return myQAPanel;
    }

    /**
     * Draws the question/answer panel for a short answer question.
     *
     * @param theQueryText   the query.
     * @return the question/answer panel.
     */
    private JPanel drawQAPanel(final String theQueryText) {
        myQAPanel = new JPanel(new BorderLayout());
        myQAPanel.add(drawQuestionArea(theQueryText), BorderLayout.CENTER);
        myQAPanel.add(drawShortAnswerPanel(), BorderLayout.SOUTH);
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
        myQuestionPanel.setBorder(GENERAL_BORDER);

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

    private JPanel drawMultipleChoicePanel(final String[] theAnswerArray) {
        int numberOfAnswers = theAnswerArray.length;
        myAnswerPanel = drawAnswerPanel();
        myResponsePanel = new JPanel(new GridLayout(numberOfAnswers, 1));
        myResponsePanel.setBorder(GENERAL_BORDER);
        myResponsePanel.setBackground(DARK_GREY);

        myAnswerButtons = new JRadioButton[numberOfAnswers];
        myAnswerButtonsGroup = new ButtonGroup();
        for (int i = 0; i < numberOfAnswers; i++) {
            myAnswerButtons[i] = buildRadioButton(theAnswerArray[i]);
            myAnswerButtonsGroup.add(myAnswerButtons[i]);
            myResponsePanel.add(myAnswerButtons[i]);
        }

        myAnswerPanel.add(myResponsePanel, BorderLayout.CENTER);
        myAnswerPanel.add(drawAnswerSubmitPanel(), BorderLayout.SOUTH);
        return myAnswerPanel;
    }

    private JPanel drawShortAnswerPanel() {
        myAnswerPanel = drawAnswerPanel();
        myResponsePanel = new JPanel(new BorderLayout());
        myResponsePanel.setBackground(DARK_GREY);

        myAnswerPrompt = new JTextField();
        myAnswerPrompt.setEditable(true);
        myAnswerPrompt.setFont(BUTTON_FONT);
        myAnswerPrompt.setBackground(DARK_GREY);
        myAnswerPrompt.setForeground(WHITE);
        myAnswerPrompt.setCaretColor(WHITE);
        myResponsePanel.setBackground(MID_GREY);
        final JLabel textIndicator = new JLabel("> ");
        textIndicator.setFont(BUTTON_FONT);
        textIndicator.setForeground(WHITE);
        myResponsePanel.add(textIndicator, BorderLayout.WEST);
        myResponsePanel.add(myAnswerPrompt, BorderLayout.CENTER);

        myAnswerPanel.add(myResponsePanel, BorderLayout.CENTER);
        myAnswerPanel.add(drawAnswerSubmitPanel(), BorderLayout.SOUTH);

        return myAnswerPanel;
    }

    private JPanel drawAnswerPanel() {
        myAnswerPanel = new JPanel(new BorderLayout());
        myAnswerPanel.setBorder(ANSWER_PADDING);
        myAnswerPanel.setBackground(MID_GREY);
        return myAnswerPanel;
    }

    private JPanel drawAnswerSubmitPanel() {
        myAnswerSubmissionPanel = new JPanel(new GridLayout(1, 2));
        mySubmitButton = buildButton("Submit");
        myCancelButton = buildButton("Cancel");
        myAnswerSubmissionPanel.add(mySubmitButton);
        myAnswerSubmissionPanel.add(myCancelButton);
        myAnswerSubmissionPanel.setBorder(ANSWER_PADDING);
        myAnswerSubmissionPanel.setBackground(MID_GREY);
        return myAnswerSubmissionPanel;
    }

    /**
     * Draws the direction controls.
     *
     * @return the direction control panel.
     */
    private JPanel drawDirectionControls() {
        myDirectionPanel = new JPanel(new GridLayout(3, 3));
        myNorthButton = buildButton(DIRECTION_TEXT[0]);
        myWestButton = buildButton(DIRECTION_TEXT[1]);
        myEastButton = buildButton(DIRECTION_TEXT[2]);
        mySouthButton = buildButton(DIRECTION_TEXT[3]);
        final JButton[] directionButtons = {myNorthButton, myWestButton,
                myEastButton, mySouthButton};
        myDirectionPanel.add(buildBufferPanel());
        for (JButton button : directionButtons) {
            myDirectionPanel.add(button);
            myDirectionPanel.add(buildBufferPanel());
        }
        myDirectionPanel.setBorder(DIRECTION_PADDING);
        myDirectionPanel.setBackground(MID_GREY);
        return myDirectionPanel;
    }

    TriviaMaze getController() {
        return myController;
    }

    JPanel getContentPanel() {
        return myContentPanel;
    }

    void updateMapDisplay() {
        myGamePanel.remove(myMapDisplay);
        myMapDisplay = buildMapDisplay(myController.getMazeCharArray());
        myGamePanel.add(myMapDisplay, BorderLayout.CENTER);
    }

    private class updateGui extends AbstractAction {

        private final Direction myDirection;

        private updateGui(final Direction theDirection) {
            myDirection = theDirection;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            if (myMovementEnabledStatus) {
                doMove(myDirection);
                myFrame.revalidate();
                myFrame.repaint();
            }
        }
    }
}

