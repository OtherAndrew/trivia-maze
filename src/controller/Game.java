package controller;

import model.Maze;

import java.util.Scanner;

import static model.mazecomponents.Direction.*;

public class Game {

    public static void game() {
        Maze triviaMaze = new Maze(7, 7);
        Scanner s = new Scanner(System.in);
        while (!triviaMaze.atGoal()) {
            System.out.println(triviaMaze.toString());
            System.out.println(triviaMaze.getPlayerLocation().getX() + ", " + triviaMaze.getPlayerLocation().getY());
            switch (s.nextLine()) {
                case "w" -> triviaMaze.move(NORTH);
                case "a" -> triviaMaze.move(WEST);
                case "s" -> triviaMaze.move(SOUTH);
                case "d" -> triviaMaze.move(EAST);
            }
        }
    }

    public static void main(String[] args) {
        game();
    }
}
