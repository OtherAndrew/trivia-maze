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

    /**
     * Creates TriviaMaze
     *
     * @param theMasterKey  ability to open doors without answering a question.
     * @param theXRay       reveals entire maze.
     */
    public TriviaMaze(final boolean theMasterKey,
                      final boolean theXRay) {
        myMasterKey = theMasterKey;
        myXRay = theXRay;
    }

    public TriviaMaze() {
        this(false, false);
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
            myMaze.getPlayerLocation().setDoorState(theDirection, State.OPEN);
        }
        myMaze.attemptMove(theDirection);
    }

    public void respond(final Direction theDirection, final String myAnswer) {
        myMaze.respond(theDirection, myAnswer);
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

    public void endGame(final boolean theWinStatus) {
        myGUI.displayEndGame(theWinStatus);
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
        myGUI.updateMapDisplay(theReveal);
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
