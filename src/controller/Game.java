package controller;

import model.Maze;
import model.questions.Question;

import java.util.Optional;
import java.util.Scanner;

import static model.mazecomponents.Direction.*;

public class Game {

    public static void game() {
        Maze triviaMaze = new Maze(6, 6);
        Scanner s = new Scanner(System.in);
        Optional<Question> q = Optional.empty();
        while (!triviaMaze.atGoal()) {
            System.out.println(triviaMaze);
            System.out.println(triviaMaze.getPlayerLocation().getRow() + ", " + triviaMaze.getPlayerLocation().getCol());
            switch (s.nextLine()) {
                case "w" -> q = triviaMaze.move(NORTH);
                case "a" -> q = triviaMaze.move(WEST);
                case "s" -> q = triviaMaze.move(SOUTH);
                case "d" -> q = triviaMaze.move(EAST);
            }
            if (q.isPresent()) {
                System.out.println(q);
            }
        }
        System.out.println(triviaMaze);
    }

    public static void main(String[] args) {
        game();
    }
}
