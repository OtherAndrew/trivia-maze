package controller;

import model.Maze;
import model.mazecomponents.Direction;
import model.mazecomponents.State;
import view.Game;

import static model.mazecomponents.State.OPEN;

public class TriviaMaze {

    private Game myGUI;
    private Maze myMaze;

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
        // PLACEHOLDER
        myMaze.getPlayerLocation().setDoorState(theDirection, OPEN);
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


    public static void main(final String[] theArgs) {
        final TriviaMaze triviaMaze = new TriviaMaze();
        final Maze maze = new Maze(triviaMaze, 4, 4); // Placeholder
        final Game game = new Game(triviaMaze);
    }
}
