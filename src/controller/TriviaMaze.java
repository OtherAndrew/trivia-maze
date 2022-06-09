package controller;

import model.Maze;
import model.mazecomponents.Direction;
import model.mazecomponents.State;
import view.Game;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * TriviaMaze is a game wherein a player attempts to navigate a maze to reach
 * the exit and achieve victory. Along the way, they must answer trivia
 * questions to progress; incorrect responses bar the way and may result in a
 * loss if the goal becomes impossible to reach.
 */
public class TriviaMaze {

    /**
     * Driver method for main entry point into application.
     *
     * @param theArgs   command line arguments.
     */
    public static void main(final String[] theArgs) {
        final TriviaMaze triviaMaze = new TriviaMaze();
        new Maze(triviaMaze, 4, 4);
        new Game(triviaMaze);
    }

    /**
     * The View.
     */
    private Game myGUI;
    /**
     * The Model.
     */
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
     * Instantiates a new Trivia maze.
     */
    public TriviaMaze() {
        myMasterKey = false;
        myXRay = false;
    }

    /**
     * Register view.
     *
     * @param theGUI    the View.
     */
    public void registerView(final Game theGUI) {
        myGUI = theGUI;
    }

    /**
     * Register model.
     *
     * @param theMaze   the Model.
     */
    public void registerModel(final Maze theMaze) {
        myMaze = theMaze;
    }

    /**
     * Builds a new maze.
     *
     * @param theRows the number of rows in the maze.
     * @param theCols the number of columns in the maze.
     */
    public void buildMaze(final int theRows, final int theCols) {
        myMaze.build(theRows, theCols);
    }

    /**
     * Sets master key enabled.
     *
     * @param theStatus ability to open doors without answering a question.
     */
    public void setMasterKeyEnabled(final boolean theStatus) {
        myMasterKey = theStatus;
    }

    /**
     * Sets x-ray enabled.
     *
     * @param theStatus reveals entire maze.
     */
    public void setXRayEnabled(final boolean theStatus) {
        myXRay = theStatus;
    }

    /**
     * Move the player in the specified direction.
     *
     * @param theDirection  the given direction.
     */
    public void move(final Direction theDirection) {
        if (myMasterKey) myMaze.getPlayerLocation().setDoorState(theDirection, State.OPENED);
        myMaze.attemptMove(theDirection);
    }

    /**
     * Submit an answer for a door in a specified direction.
     *
     * @param theDirection the given direction.
     * @param myAnswer     the answer.
     */
    public void respond(final Direction theDirection, final String myAnswer) {
        myMaze.respond(theDirection, myAnswer);
    }

    /**
     * Gets visited or unvisited count.
     *
     * @param theVisited whether to check the visited or unvisited rooms.
     * @return the count for the specified type of room.
     */
    public int getVisitCount(final boolean theVisited) {
        return myMaze.getRoomVisitedNum(theVisited);
    }

    /**
     * Gets door count of the specified state.
     *
     * @param theState the given state.
     * @return the maze door count.
     */
    public int getMazeDoorCount(final State theState) {
        return myMaze.getDoorStateNum(theState);
    }

    /**
     * Trigger the end game based on whether victory or defeat occurred.
     *
     * @param theWinStatus whether victory or defeat was the result.
     */
    public void endGame(final boolean theWinStatus) {
        myGUI.displayEndGame(theWinStatus);
    }

    /**
     * Get maze character array representation.
     *
     * @return the representation.
     */
    public char[][] getMazeCharArray() {
        return myMaze.toCharArray();
    }

    /**
     * Get dummy maze character array representation.
     *
     * @return the representation.
     */
    public char[][] getDummyCharArray() {
        return myMaze.generateDummy();
    }

    /**
     * Update the question and answer interface with no question.
     */
    public void updateQA() {
        myGUI.updateQA();
    }

    /**
     * Update the question and answer interface based on a question.
     *
     * @param theQuery   the question.
     * @param theAnswers the answers.
     */
    public void updateQA(final String theQuery,
                         final List<String> theAnswers) {
        if (theAnswers instanceof ArrayList<String>) myGUI.updateQA(theQuery);
        else if (theAnswers instanceof LinkedList<String>) myGUI.updateQA(theQuery, theAnswers);
    }

    /**
     * Update map.
     *
     * @param theReveal whether the map should be fully revealed or not.
     */
    public void updateMap(final boolean theReveal) {
        myGUI.updateMapDisplay(theReveal || myXRay);
    }

    /**
     * Quick save.
     */
    public void quickSave() {
        myMaze.save();
    }

    /**
     * Save.
     *
     * @param theFile the file to save.
     */
    public void save(final File theFile) {
        myMaze.save(theFile);
    }

    /**
     * Quick load.
     *
     * @return whether the load was successful.
     */
    public boolean quickLoad() {
        return myMaze.load();
    }

    /**
     * Load.
     *
     * @param theFile the file to load.
     */
    public void load(final File theFile) {
        myMaze.load(theFile);
    }
}
