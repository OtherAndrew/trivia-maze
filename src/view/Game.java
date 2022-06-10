package view;

import controller.TriviaMaze;
import model.mazecomponents.Direction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.StringJoiner;

import static java.awt.BorderLayout.*;
import static java.awt.event.KeyEvent.*;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import static javax.swing.KeyStroke.getKeyStroke;
import static model.mazecomponents.State.*;
import static view.AppTheme.*;
import static view.FileAccessor.getFileAccessor;
import static view.MazeDisplayBuilder.buildMapDisplay;

/**
 * Game is the main screen for gameplay in the application. It displays a map
 * of a maze for navigation, buttons to traverse that map, and an interface for
 * questions to be shown and answers to be inputted or selected.
 */
public class Game {

    /**
     * Border of directions.
     */
    public final static EmptyBorder DIRECTION_PADDING = new EmptyBorder(17, 0, 7, 0);
    /**
     * Border of sidebar.
     */
    public final static EmptyBorder SIDEBAR_PADDING = new EmptyBorder(0, 7, 0, 0);
    /**
     * Border of answers.
     */
    public final static EmptyBorder ANSWER_PADDING = new EmptyBorder(7, 0, 0, 0);

    /**
     * Whether the user can input text.
     */
    private boolean myTextInputEnabled;
    /**
     * Whether the user can move.
     */
    private boolean myMovementEnabled;
    /**
     * Whether the user can save.
     */
    private boolean mySaveEnabled;
    /**
     * User's chosen direction to move.
     */
    private Direction myDirection;
    /**
     * User's chosen answer to a question.
     */
    private String myAnswer;
    /**
     * Cancel a question action.
     */
    private final AbstractAction myCancelFunction;
    /**
     * Submit an answer action.
     */
    private final AbstractAction mySubmitFunction;
    /**
     * The controller for the GUI.
     */
    private final TriviaMaze myController;
    /**
     * Contains all panels.
     */
    private final JFrame myFrame;
    /**
     * Contains start, difficulty, and game screens.
     */
    private final JPanel myContentPanel;
    /**
     * The overall game screen.
     */
    private JPanel myGamePanel;
    /**
     * A menubar to navigate the game screen options.
     */
    private JPanel myMenuBar;
    /**
     * Displays the map.
     */
    private JPanel myMapDisplay;
    /**
     *  Interface for question/answer and movement.
     */
    private JPanel mySidebar;
    /**
     * Interface for questions and answers.
     */
    private JPanel myQAPanel;
    /**
     * Displays the question.
     */
    private JPanel myQuestionPanel;
    /**
     * Answering and interacting with a question.
     */
    private JPanel myAnswerPanel;
    /**
     * Input for answering question.
     */
    private JPanel myResponsePanel;
    /**
     * Button panel for moving.
     */
    private JPanel myDirectionPanel;
    /**
     * Button panel for interacting with question.
     */
    private JPanel myAnswerSubmissionPanel;
    /**
     * Move north.
     */
    private JButton myNorthButton;
    /**
     * Move west.
     */
    private JButton myWestButton;
    /**
     * Move east.
     */
    private JButton myEastButton;
    /**
     * Move south.
     */
    private JButton mySouthButton;
    /**
     * Start a new maze.
     */
    private JButton myNewGameButton;
    /**
     * Quick save preset file.
     */
    private JButton myQuickSaveButton;
    /**
     * Quick load preset file.
     */
    private JButton myQuickLoadButton;
    /**
     * Save a file.
     */
    private JButton mySaveButton;
    /**
     * Load a file.
     */
    private JButton myLoadButton;
    /**
     * Submit an answer to a question.
     */
    private JButton mySubmitButton;
    /**
     * Cancel out of answering a question.
     */
    private JButton myCancelButton;
    /**
     * The container for question text.
     */
    private JTextArea myQuestionArea;
    /**
     * Input for answer text.
     */
    private JTextField myAnswerPrompt;
    /**
     * Buttons for answer choices.
     */
    private JRadioButton[] myAnswerButtons;
    /**
     * Group of answer choices.
     */
    private ButtonGroup myAnswerButtonsGroup;


    /**
     * Creates a game screen.
     *
     * @param theController the controller.
     */
    public Game(final TriviaMaze theController) {
        myController = theController;
        theController.registerView(this);

        System.setProperty("awt.useSystemAAFontSettings", "on");
        myFrame = buildFrame();
        myContentPanel = buildPanel();
        myContentPanel.setLayout(new CardLayout());
        myContentPanel.add(new Start(this), "start");
        myContentPanel.add(new Difficulty(this), "difficulty");
        myContentPanel.add(drawGamePanel(), "game");

        myNewGameButton.addActionListener(e -> show("difficulty"));
        myQuickSaveButton.addActionListener(e -> { if (mySaveEnabled)
            myController.quickSave();
        });
        myQuickLoadButton.addActionListener(e -> { if (myController.quickLoad())
            show("game");
        });
        mySaveButton.addActionListener(e -> { if (mySaveEnabled)
            getFileAccessor().saveFile(myContentPanel).ifPresent(myController::save);
        });
        myLoadButton.addActionListener(e ->
                getFileAccessor().loadFile(myContentPanel).ifPresent(this::load));

        myCancelFunction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myAnswer = null;
                updateQA();
            }
        };
        mySubmitFunction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myTextInputEnabled) {
                    myAnswer = myAnswerPrompt.getText();
                    if (!myAnswer.isEmpty()) {
                        myController.respond(myDirection, myAnswer);
                        myAnswerPrompt.setText("");
                    }
                } else {
                    if (myAnswer != null) {
                        myController.respond(myDirection, myAnswer);
                    }
                }
                myAnswer = null;
            }
        };

        final move north = new move(Direction.NORTH);
        final move east = new move(Direction.EAST);
        final move south = new move(Direction.SOUTH);
        final move west = new move(Direction.WEST);
        addActionListeners(north, east, south, west);
        addKeyboardBindings(north, east, south, west);

        myFrame.add(myContentPanel);
        myFrame.setVisible(true);
    }

    /**
     * Add actions to buttons.
     *
     * @param theDirections moves in certain directions.
     */
    private void addActionListeners(final move... theDirections) {
        myNorthButton.addActionListener(theDirections[0]);
        myEastButton.addActionListener(theDirections[1]);
        mySouthButton.addActionListener(theDirections[2]);
        myWestButton.addActionListener(theDirections[3]);
    }

    /**
     * Add keyboard shortcuts.
     *
     * @param theDirections moves in certain directions.
     */
    private void addKeyboardBindings(final move... theDirections) {
        InputMap iMap = (InputMap) UIManager.get("Button.focusInputMap");
        iMap.put(getKeyStroke(VK_SPACE, 0), "none");
        iMap = myGamePanel.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        iMap.put(getKeyStroke("W"), "moveNorth");
        iMap.put(getKeyStroke("D"), "moveEast");
        iMap.put(getKeyStroke("S"), "moveSouth");
        iMap.put(getKeyStroke("A"), "moveWest");
        iMap.put(getKeyStroke(VK_UP, 0), "moveNorth");
        iMap.put(getKeyStroke(VK_RIGHT, 0), "moveEast");
        iMap.put(getKeyStroke(VK_DOWN, 0), "moveSouth");
        iMap.put(getKeyStroke(VK_LEFT, 0), "moveWest");
        iMap.put(getKeyStroke(VK_ENTER, 0), "submit");
        iMap.put(getKeyStroke(VK_ESCAPE, 0), "cancel");
        final ActionMap aMap = myGamePanel.getActionMap();
        aMap.put("moveNorth", theDirections[0]);
        aMap.put("moveEast", theDirections[1]);
        aMap.put("moveSouth", theDirections[2]);
        aMap.put("moveWest", theDirections[3]);
        aMap.put("submit", mySubmitFunction);
        aMap.put("cancel", myCancelFunction);
    }

    /**
     * Display game over message and initiate end of game.
     *
     * @param theWinStatus whether the player won or not.
     */
    public void displayEndGame(final boolean theWinStatus) {
        final StringJoiner sj = new StringJoiner("\n");
        if (theWinStatus) sj.add("You won!");
        else sj.add("You lost!\n");
        sj.add("Rooms visited: " + myController.getVisitCount(true));
        sj.add("Rooms not visited: " + myController.getVisitCount(false) + "\n");
        sj.add("Opened doors: " + myController.getMazeDoorCount(OPENED));
        sj.add("Closed doors: " + myController.getMazeDoorCount(CLOSED));
        sj.add("Locked doors: " + myController.getMazeDoorCount(LOCKED));
        sj.add("Undiscovered doors: " + myController.getMazeDoorCount(UNDISCOVERED));
        myQuestionArea.setText(sj.toString());
        updateMapDisplay(true);
        myMovementEnabled = false;
        mySaveEnabled = false;
    }

    /**
     * Draws the overall game panel.
     *
     * @return the overall game panel.
     */
    private JPanel drawGamePanel() {
        myGamePanel = buildPanel();
        myGamePanel.add(drawMenuBar(), NORTH);
        myMapDisplay = buildMapDisplay(myController.getMazeCharArray());
        myGamePanel.add(myMapDisplay, CENTER);
        myGamePanel.add(drawSidebar(), EAST);
        return myGamePanel;
    }

    /**
     * Draws the game sidebar.
     *
     * @return the game sidebar.
     */
    private JPanel drawSidebar() {
        mySidebar = new JPanel(new BorderLayout());
        mySidebar.setBorder(SIDEBAR_PADDING);
        mySidebar.setBackground(MID_GREY);
        mySidebar.add(drawDirectionControls(), SOUTH);
        mySidebar.add(drawQAPanel(), CENTER);
        return mySidebar;
    }

    /**
     * Draws the game menubar.
     *
     * @return the game menubar.
     */
    private JPanel drawMenuBar() {
        myNewGameButton = buildButton("New Game");
        myQuickSaveButton = buildButton("Q.Save");
        myQuickLoadButton = buildButton("Q.Load");
        mySaveButton = buildButton("Save");
        myLoadButton = buildButton("Load");
        myMenuBar = buildMenubar(myNewGameButton, myQuickSaveButton,
                myQuickLoadButton, mySaveButton, myLoadButton);
        return myMenuBar;
    }

    /**
     * Draws an empty question answer panel.
     *
     * @return an empty question answer panel.
     */
    private JPanel drawQAPanel() {
        myQAPanel = drawGenQAPanel();
        myQAPanel.add(drawQuestionArea(""), CENTER);
        return myQAPanel;
    }

    /**
     * Draws the question/answer panel for a short answer question.
     *
     * @param theQueryText the query.
     * @return the question/answer panel.
     */
    private JPanel drawQAPanel(final String theQueryText) {
        myQAPanel = drawGenQAPanel();
        myQAPanel.add(drawQuestionArea(theQueryText), CENTER);
        myQAPanel.add(drawShortAnswerPanel(), SOUTH);
        return myQAPanel;
    }

    /**
     * Draws the question/answer panel for a multiple choice question.
     *
     * @param theQueryText   the query.
     * @param theAnswerArray a set of answers.
     * @return the question/answer panel.
     */
    private JPanel drawQAPanel(final String theQueryText,
                               final List<String> theAnswerArray) {
        myQAPanel = drawGenQAPanel();
        myQAPanel.add(drawQuestionArea(theQueryText), CENTER);
        myQAPanel.add(drawMultipleChoicePanel(theAnswerArray), SOUTH);
        return myQAPanel;
    }

    /**
     * Draws a generic question/answer panel.
     *
     * @return a generic question answer panel.
     */
    private JPanel drawGenQAPanel() {
        myQAPanel = new JPanel(new BorderLayout());
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
        myQuestionPanel.add(myQuestionArea, CENTER);
        return myQuestionPanel;
    }

    /**
     * Draws a multiple choice answer panel.
     *
     * @return a multiple choice answer panel.
     */
    private JPanel drawMultipleChoicePanel(final List<String> theAnswerArray) {
        myTextInputEnabled = false;
        int numberOfAnswers = theAnswerArray.size();
        myAnswerPanel = drawAnswerPanel();
        myResponsePanel = new JPanel(new GridLayout(numberOfAnswers, 1));
        myResponsePanel.setBorder(GENERAL_BORDER);
        myResponsePanel.setBackground(DARK_GREY);

        myAnswerButtons = new JRadioButton[numberOfAnswers];
        myAnswerButtonsGroup = new ButtonGroup();
        for (int i = 0; i < numberOfAnswers; i++) {
            final String answer = theAnswerArray.get(i);
            myAnswerButtons[i] = buildRadioButton(answer);
            myAnswerButtons[i].addActionListener(e -> myAnswer = answer.substring(0, 1));
            myAnswerButtons[i].getInputMap().put(
                    getKeyStroke(VK_SPACE, 0), "submit");
            myAnswerButtons[i].getActionMap().put("submit", mySubmitFunction);
            myAnswerButtonsGroup.add(myAnswerButtons[i]);
            myResponsePanel.add(myAnswerButtons[i]);
        }

        myAnswerPanel.add(myResponsePanel, CENTER);
        myAnswerPanel.add(drawAnswerSubmitPanel(), SOUTH);
        return myAnswerPanel;
    }

    /**
     * Draws a short answer panel.
     *
     * @return a short answer panel.
     */
    private JPanel drawShortAnswerPanel() {
        myTextInputEnabled = true;
        myAnswerPanel = drawAnswerPanel();
        myResponsePanel = new JPanel(new BorderLayout());
        myResponsePanel.setBackground(MID_GREY);

        myAnswerPrompt = new JTextField();
        myAnswerPrompt.setEditable(true);
        myAnswerPrompt.setFont(BUTTON_FONT);
        myAnswerPrompt.setBackground(DARK_GREY);
        myAnswerPrompt.setForeground(WHITE);
        myAnswerPrompt.setCaretColor(WHITE);

        final JLabel textIndicator = new JLabel("> ");
        textIndicator.setFont(BUTTON_FONT);
        textIndicator.setForeground(WHITE);
        myResponsePanel.add(textIndicator, WEST);
        myResponsePanel.add(myAnswerPrompt, CENTER);

        myAnswerPanel.add(myResponsePanel, CENTER);
        myAnswerPanel.add(drawAnswerSubmitPanel(), SOUTH);

        return myAnswerPanel;
    }

    /**
     * Draws a baseline answer panel.
     *
     * @return a baseline answer panel.
     */
    private JPanel drawAnswerPanel() {
        myAnswerPanel = new JPanel(new BorderLayout());
        myAnswerPanel.setBorder(ANSWER_PADDING);
        myAnswerPanel.setBackground(MID_GREY);
        return myAnswerPanel;
    }

    /**
     * Draws the answer submit panel.
     *
     * @return the answer submit panel.
     */
    private JPanel drawAnswerSubmitPanel() {
        myAnswerSubmissionPanel = new JPanel(new GridLayout(1, 2));
        mySubmitButton = buildButton("Submit");
        mySubmitButton.setBackground(BLUE);
        myCancelButton = buildButton("Cancel");
        myCancelButton.setBackground(YELLOW);
        mySubmitButton.addActionListener(mySubmitFunction);
        myCancelButton.addActionListener(myCancelFunction);
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
        myNorthButton = buildButton("Up");
        myWestButton = buildButton("Left");
        myEastButton = buildButton("Right");
        mySouthButton = buildButton("Down");
        myDirectionPanel.add(buildBufferPanel());
        for (JButton button : new JButton[]{myNorthButton, myWestButton, myEastButton, mySouthButton}) {
            myDirectionPanel.add(button);
            myDirectionPanel.add(buildBufferPanel());
        }
        myDirectionPanel.setBorder(DIRECTION_PADDING);
        myDirectionPanel.setBackground(MID_GREY);
        return myDirectionPanel;
    }

    /**
     * Gets controller.
     *
     * @return the controller.
     */
    TriviaMaze getController() {
        return myController;
    }

    /**
     * Update map display.
     *
     * @param theReveal whether the entire map is visible or not.
     */
    public void updateMapDisplay(final boolean theReveal) {
        myMovementEnabled = true;
        mySaveEnabled = true;
        myGamePanel.remove(myMapDisplay);
        myMapDisplay = buildMapDisplay(myController.getMazeCharArray(), theReveal);
        myGamePanel.add(myMapDisplay, CENTER);
        myFrame.revalidate();
        myFrame.repaint();
    }

    /**
     * Replaces a panel and then refreshes the frame.
     *
     * @param theContainer  from where to remove the panel.
     * @param theReplaced   the panel to remove.
     * @param theReplacer   the panel to add.
     */
    private void update(final JPanel theContainer, final JPanel theReplaced,
                        final JPanel theReplacer) {
        theContainer.remove(theReplaced);
        theContainer.add(theReplacer, CENTER);
        myFrame.revalidate();
        myFrame.repaint();
    }

    /**
     * Clears the question answer interface.
     */
    public void updateQA() {
        myMovementEnabled = true;
        update(mySidebar, myQAPanel, drawQAPanel());
    }

    /**
     * Update question answer interface with a short answer question.
     *
     * @param theQuery the question.
     */
    public void updateQA(final String theQuery) {
        update(mySidebar, myQAPanel, drawQAPanel(theQuery));
        myAnswerPrompt.requestFocus();
    }

    /**
     * Update question answer interface with a multiple choice question.
     *
     * @param theQuery   the question.
     * @param theAnswers the answer choices.
     */
    public void updateQA(final String theQuery, final List<String> theAnswers) {
        update(mySidebar, myQAPanel, drawQAPanel(theQuery, theAnswers));
    }

    /**
     * Swap to the specified card.
     *
     * @param theCard   the card to swap to.
     */
    public void show(final String theCard) {
        updateQA();
        final CardLayout cards = (CardLayout) myContentPanel.getLayout();
        cards.show(myContentPanel, theCard);
    }

    /**
     * Loads a file and then swap to the game screen.
     *
     * @param theFile the file containing the game.
     */
    public void load(final File theFile) {
        myController.load(theFile);
        show("game");
    }

    /**
     * Move in a certain direction.
     */
    private class move extends AbstractAction {

        /**
         * The direction to move.
         */
        private final Direction mySetDirection;

        /**
         * Creates a move.
         *
         * @param theDirection  the direction to move in.
         */
        private move(final Direction theDirection) {
            mySetDirection = theDirection;
        }

        /**
         * Try to move in specific direction if enabled.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            if (myMovementEnabled) {
                myMovementEnabled = false;
                myDirection = mySetDirection;
                myController.move(myDirection);
            }
        }
    }
}
