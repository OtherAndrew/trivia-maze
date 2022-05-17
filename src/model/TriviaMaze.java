package model;

import model.mazecomponents.*;
import model.questions.Question;

import java.util.Scanner;

public class TriviaMaze {

    /**
     * The maze.
     */
    private static Maze myMaze;


    public static void main(final String[] theArgs) {
        myMaze = new Maze(10, 10);
        // introMessage();
        do {
            System.out.println(myMaze.toString());
            Scanner input = new Scanner(System.in);
            System.out.print("Choose a direction to move in (E, W, N, S): ");
            String direction = input.nextLine();
            boolean moved = switch (direction.toUpperCase()) {
                case "D" -> movePlayer(myMaze, Direction.EAST, input);
                case "A" -> movePlayer(myMaze, Direction.WEST, input);
                case "W" -> movePlayer(myMaze, Direction.NORTH, input);
                case "S" -> movePlayer(myMaze, Direction.SOUTH, input);
                default -> false;
            };
        } while (!myMaze.atGoal());
    }

    private static boolean movePlayer(Maze theMaze, final Direction theDirection, final Scanner theInput) {
        Question question = theMaze.getQuestion(theDirection);
        System.out.println(question.toString());
        System.out.print("Select an option (A, B, C, D): ");
        String selection = theInput.nextLine();
        boolean correctness = question.checkAnswer(selection);
        if (correctness) {
            myMaze.changeDoorState(theDirection, State.OPEN);
            boolean moved = myMaze.move(theDirection);
//            if (!moved) {
//                throw new Exception("Player failed to move.");
//            }
        } else {
            return false;
        }
        return true;
    }
}
