package controller;

import model.Maze;
import model.mazecomponents.Direction;
import model.mazecomponents.State;
import view.Game;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TriviaMaze {

    public static void main(final String[] theArgs) {
        final TriviaMaze triviaMaze = new TriviaMaze();
        final Maze maze = new Maze(triviaMaze, 4, 4);
        final Game game = new Game(triviaMaze);
    }

    private Game myGUI;
    private Maze myMaze;

    /**
     * Open doors without answering a question.
     */
    private boolean myMasterKey;

    /**
     * Omniscient view of maze.
     */
    private boolean myXRay;

    public TriviaMaze() {
        myMasterKey = false;
        myXRay = false;
    }

    public void registerView(final Game theGUI) {
        myGUI = theGUI;
    }

    public void registerModel(final Maze theMaze) {
        myMaze = theMaze;
    }

    /**
     * Builds a new maze.
     *
     * @param theRows       the number of rows in the maze.
     * @param theCols       the number of columns in the maze.
     */
    public void buildMaze(final int theRows, final int theCols) {
        myMaze.build(theRows, theCols);
    }

    /**
     *
     * @param theStatus  ability to open doors without answering a question.
     */
    public void setMasterKeyEnabled(final boolean theStatus) {
        myMasterKey = theStatus;
    }

    /**
     *
     * @param theStatus reveals entire maze.
     */
    public void setXRayEnabled(final boolean theStatus) {
        myXRay = theStatus;
    }

    public void move(final Direction theDirection) {
        if (myMasterKey) {
            myMaze.getPlayerLocation().setDoorState(theDirection, State.OPENED);
        }
        myMaze.attemptMove(theDirection);
    }

    public void respond(final Direction theDirection, final String myAnswer) {
        myMaze.respond(theDirection, myAnswer);
    }

    public int getVisitCount(final boolean theVisited) {
        return myMaze.getRoomVisitedNum(theVisited);
    }

    public int getMazeDoorCount(final State theState) {
        return myMaze.getDoorStateNum(theState);
    }

    public void endGame(final boolean theWinStatus) {
        myGUI.displayEndGame(theWinStatus);
    }

    public char[][] getMazeCharArray() {
        return myMaze.toCharArray();
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

    public void updateMap(final boolean theReveal) {
        myGUI.updateMapDisplay(theReveal || myXRay);
    }

    public void quickSave() {
        myMaze.save();
    }

    public void save(final File theFile) {
        myMaze.save(theFile);
    }

    public boolean quickLoad() {
        return myMaze.load();
    }

    public void load(final File theFile) {
        myMaze.load(theFile);
    }
}
