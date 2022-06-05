package controller;

import model.Maze;
import model.mazecomponents.Direction;
import model.mazecomponents.State;
import view.Game;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static model.mazecomponents.State.OPEN;

public class TriviaMaze {

    public static void main(final String[] theArgs) {
        final TriviaMaze triviaMaze = new TriviaMaze(false, false, false);
        final Maze maze = new Maze(triviaMaze, 4, 4); // Placeholder
        final Game game = new Game(triviaMaze);
    }

    private Game myGUI;
    private Maze myMaze;

    /**
     * Open doors without answering a question.
     */
    private boolean myMasterKey;

    /**
     * Correct answers are highlighted.
     */
    private boolean myAllKnowing;

    /**
     * Omniscient view of maze.
     */
    private boolean myXRay;

    /**
     * Creates TriviaMaze
     *
     * @param theMasterKey  ability to open doors without answering a question.
     * @param theAllKnowing ability to see correct answer.
     * @param theXRay       reveals entire maze.
     */
    public TriviaMaze(final boolean theMasterKey, final boolean theAllKnowing,
                      final boolean theXRay) {
        myMasterKey = theMasterKey;
        myAllKnowing = theAllKnowing;
        myXRay = theXRay;
    }

    public TriviaMaze() {
        this(false, false, false);
    }

    public void registerView(final Game theGUI) {
        myGUI = theGUI;
    }

    public void registerModel(final Maze theMaze) {
        myMaze = theMaze;
    }

    public void buildMaze(final int theRows, final int theCols) {
        myMaze.build(theRows, theCols);
    }

    public void move(final Direction theDirection) {
        if (myMasterKey) {
            myMaze.getPlayerLocation().setDoorState(theDirection, OPEN);
        }
        // myMaze.getPlayerLocation().setDoorState(theDirection, OPEN);
        myMaze.attemptMove(theDirection);
    }

    public char[][] getMazeCharArray() {
        return myMaze.toCharArray();
    }

    public int getVisitCount(final boolean theVisited) {
        return myMaze.getRoomVisitedNum(theVisited);
    }

    public int getMazeDoorCount(final State theState) {
        return myMaze.getDoorStateNum(theState);
    }

    public boolean getVictory() {
        return myMaze.atGoal();
    }

    public boolean getLoss() {
        return myMaze.gameLoss();
    }

    public char[][] getDummyCharArray() {
        return myMaze.generateDummy();
    }

    public void updateQA() {
        myGUI.updateQA();
    }

    public void updateQA(final String theQuery,
                         final List<String> theAnswers) {
        if (theAnswers instanceof ArrayList<String>) {
            myGUI.updateQA(theQuery);
        } else if (theAnswers instanceof LinkedList<String>) {
            myGUI.updateQA(theQuery, theAnswers);
        }
    }

    public void respond(final Direction theDirection, final String myAnswer) {
        myMaze.respond(theDirection, myAnswer);
    }

    public void updateMap(final boolean theReveal) {
        myGUI.updateMapDisplay(theReveal);
    }

    public void save(final File theFile) {
        myMaze.save(theFile);
    }

    public void load(final File theFile) {
        myMaze.load(theFile);
        updateMap(false);
    }
}
