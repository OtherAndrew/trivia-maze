package model;

import model.*;
public class TriviaMaze {

    /**
     * The maze.
     */
    private Maze myMaze;
    public static void main(final String[] theArgs) {
        myMaze = new Maze(10, 10);
        // introMessage();
        do {
            System.out.println(myMaze.toString);
            Scanner input = new Scanner(System.in);
            System.out.print("Choose a direction to move in (E, W, N, S): ");
            String direction = input.nextLine();
            Boolean moved = false;
            switch (direction.toUpperCase()) {
                case "E":
                    // Turn into method
                    moved = movePlayer(this.myMaze, Direction.EAST);
                    break;
                case "W":
                    moved = movePlayer(this.myMaze, Direction.EAST);
                    break;
                case "N":
                    moved = movePlayer(this.myMaze, Direction.EAST);
                    break;
                case "S":
                    moved = movePlayer(this.myMaze, Direction.EAST);
                    break;
            }
        } while (myMaze.atGoal!)
    }

    private static boolean movePlayer(Maze theMaze, final Direction theDirection, final Scanner theInput) {
        Question question = theMaze.getQuestion(theDirection);
        System.out.println(question.toString());
        System.out.print("Select an option (A, B, C, D): ");
        String selection = theInput.nextLine();
        Boolean correctness = question.checkAnswer(selection);
        if (correctness) {
            myMaze.changeDoorState(theDirection, State.OPEN);
            moved = myMaze.move(theDirection);
            if (moved!)
            throw new Exception("Player failed to move.");
        } else {
            return false
        }
        return true;

    }
}
