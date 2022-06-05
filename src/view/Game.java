package view;

import controller.TriviaMaze;
import model.mazecomponents.Direction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.StringJoiner;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import static model.mazecomponents.Direction.*;
import static model.mazecomponents.State.*;
import static view.AppTheme.*;
import static view.MazeDisplayBuilder.buildMapDisplay;

public class Game {

    public final static EmptyBorder DIRECTION_PADDING = new EmptyBorder(17, 0, 7, 0);
    public final static EmptyBorder SIDEBAR_PADDING = new EmptyBorder(0, 7, 0, 0);
    public final static EmptyBorder ANSWER_PADDING = new EmptyBorder(7, 0, 0, 0);
    public static final String[] DIRECTION_TEXT = {"Up", "Left", "Right", "Down"};

    private boolean myTextInputEnabled;
    private boolean myMovementEnabled;

    private boolean mySaveEnabled;
    private Direction myDirection;
    private String myAnswer;
    private final TriviaMaze myController;
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
            myNewGameButton, myQuickSaveButton, myQuickLoadButton,
            mySaveButton, myLoadButton, mySubmitButton, myCancelButton;

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

        final CardLayout cards = new CardLayout();
        myContentPanel.setLayout(cards);

        myContentPanel.add(drawGamePanel(), "game");
        myContentPanel.add(new Difficulty(this, cards), "difficulty");
        myContentPanel.add(new Start(this, cards), "start");

        myNewGameButton.addActionListener(e -> cards.show(myContentPanel, "difficulty"));
        myQuickLoadButton.addActionListener(e -> {
            if (myController.quickLoad()) {
                updateQA();
                cards.show(myContentPanel, "game");
            }
        });
        myQuickSaveButton.addActionListener(e -> { if (mySaveEnabled) {
                myController.quickSave();
            }
        });
        mySaveButton.addActionListener(e -> { if (mySaveEnabled) {
                FileAccessor.getInstance().saveFile().ifPresent(myController::save);
            }
        });
        myLoadButton.addActionListener(e -> FileAccessor.getInstance().loadFile().ifPresent(file -> {
            myController.load(file);
            updateQA();
            cards.show(myContentPanel, "game");
        }));

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
        final InputMap iMap = myGamePanel.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        iMap.put(KeyStroke.getKeyStroke("W"), "moveNorth");
        iMap.put(KeyStroke.getKeyStroke("D"), "moveEast");
        iMap.put(KeyStroke.getKeyStroke("S"), "moveSouth");
        iMap.put(KeyStroke.getKeyStroke("A"), "moveWest");
        final ActionMap aMap = myGamePanel.getActionMap();
        aMap.put("moveNorth", theDirections[0]);
        aMap.put("moveEast", theDirections[1]);
        aMap.put("moveSouth", theDirections[2]);
        aMap.put("moveWest", theDirections[3]);
    }

    private void setMovementEnabled(final boolean theStatus) {
        myMovementEnabled = theStatus;
    }

    private void setSaveEnabled(final boolean theStatus) {
        mySaveEnabled = theStatus;
    }

    private void doMove(final Direction theDirection) {
        setMovementEnabled(false);
        myDirection = theDirection;
        myController.move(myDirection);
    }

    public void displayEndGame(final boolean theWinStatus) {
        final StringJoiner sj = new StringJoiner("\n");
        if (theWinStatus) {
            sj.add("You won!");
        } else {
            sj.add("You lost!");
        }
        sj.add("");
        sj.add("Rooms visited: " + myController.getVisitCount(true));
        sj.add("Rooms not visited: " + myController.getVisitCount(false));
        sj.add("");
        sj.add("Opened doors: " + myController.getMazeDoorCount(OPEN));
        sj.add("Closed doors: " + myController.getMazeDoorCount(CLOSED));
        sj.add("Locked doors: " + myController.getMazeDoorCount(LOCKED));
        sj.add("Undiscovered doors: " + myController.getMazeDoorCount(UNDISCOVERED));
        myQuestionArea.setText(sj.toString());
        updateMapDisplay(true);
        setMovementEnabled(false);
        setSaveEnabled(false);
    }

    private JPanel drawGamePanel() {
        myGamePanel = buildPanel();
        myGamePanel.add(drawMenuBar(), BorderLayout.NORTH);
        myMapDisplay = buildMapDisplay(myController.getMazeCharArray());
        myGamePanel.add(myMapDisplay, BorderLayout.CENTER);
        myGamePanel.add(drawSidebar(), BorderLayout.EAST);
        return myGamePanel;
    }

    private JPanel drawSidebar() {
        mySidebar = new JPanel(new BorderLayout());
        mySidebar.setBorder(SIDEBAR_PADDING);
        mySidebar.setBackground(MID_GREY);
        mySidebar.add(drawDirectionControls(), BorderLayout.SOUTH);
        mySidebar.add(drawQAPanel(), BorderLayout.CENTER);
        return mySidebar;
    }

    private JPanel drawMenuBar() {
        myNewGameButton = buildButton("New Game");
        myQuickLoadButton = buildButton("Q.Load");
        myQuickSaveButton = buildButton("Q.Save");
        mySaveButton = buildButton("Save");
        myLoadButton = buildButton("Load");
        myMenuBar = buildMenubar(myNewGameButton, myQuickLoadButton,
                myQuickSaveButton, mySaveButton, myLoadButton);
        return myMenuBar;
    }

    private JPanel drawQAPanel() {
        myQAPanel = drawGenQAPanel();
        myQAPanel.add(drawQuestionArea(), BorderLayout.CENTER);
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
        myQAPanel.add(drawQuestionArea(theQueryText), BorderLayout.CENTER);
        myQAPanel.add(drawShortAnswerPanel(), BorderLayout.SOUTH);
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
        myQAPanel.add(drawQuestionArea(theQueryText), BorderLayout.CENTER);
        myQAPanel.add(drawMultipleChoicePanel(theAnswerArray), BorderLayout.SOUTH);
        return myQAPanel;
    }

    private JPanel drawGenQAPanel() {
        myQAPanel = new JPanel(new BorderLayout());
        myQAPanel.setBackground(MID_GREY);
        return myQAPanel;
    }

    /**
     * Draws blank question area.
     *
     * @return the question area.
     */
    private JPanel drawQuestionArea() {
        return drawQuestionArea("");
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
            String answer = theAnswerArray.get(i);
            myAnswerButtons[i] = buildRadioButton(answer);
            myAnswerButtons[i].addActionListener(e -> myAnswer = answer.substring(0, 1));
            myAnswerButtonsGroup.add(myAnswerButtons[i]);
            myResponsePanel.add(myAnswerButtons[i]);
        }

        myAnswerPanel.add(myResponsePanel, BorderLayout.CENTER);
        myAnswerPanel.add(drawAnswerSubmitPanel(), BorderLayout.SOUTH);
        return myAnswerPanel;
    }

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
        mySubmitButton.addActionListener(e -> {
            if (myTextInputEnabled) {
                myController.respond(myDirection, myAnswerPrompt.getText());
                myAnswerPrompt.setText("");
            } else {
                if (myAnswer != null) {
                    myController.respond(myDirection, myAnswer);
                }
                myAnswer = null;
            }
        });
        myCancelButton.addActionListener(e -> updateQA());
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
        myDirectionPanel.add(buildBufferPanel());
        for (JButton button : new JButton[]{myNorthButton, myWestButton, myEastButton, mySouthButton}) {
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

    public void updateMapDisplay(final boolean theReveal) {
        setMovementEnabled(true);
        setSaveEnabled(true);
        myGamePanel.remove(myMapDisplay);
        myMapDisplay = buildMapDisplay(myController.getMazeCharArray(), theReveal);
        myGamePanel.add(myMapDisplay, BorderLayout.CENTER);
        myFrame.revalidate();
        myFrame.repaint();
    }

    private void update(final JPanel theContainer, final JPanel theReplacee,
                        final JPanel theReplacer) {
        theContainer.remove(theReplacee);
        theContainer.add(theReplacer, BorderLayout.CENTER);
        myFrame.revalidate();
        myFrame.repaint();
    }

    public void updateQA() {
        setMovementEnabled(true);
        update(mySidebar, myQAPanel, drawQAPanel());
    }

    public void updateQA(final String theQuery) {
        update(mySidebar, myQAPanel, drawQAPanel(theQuery));
    }

    public void updateQA(final String theQuery, final List<String> theAnswers) {
        update(mySidebar, myQAPanel, drawQAPanel(theQuery, theAnswers));
    }

    private class updateGui extends AbstractAction {

        private final Direction myDirection;

        private updateGui(final Direction theDirection) {
            myDirection = theDirection;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            if (myMovementEnabled) {
                doMove(myDirection);
            }
        }
    }
}
