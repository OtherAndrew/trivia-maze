package controller;

import model.Maze;

import java.util.Scanner;

import static model.mazecomponents.Direction.*;

public class Game {

    public static void game() {
        Maze triviaMaze = new Maze(3, 3);
        Scanner s = new Scanner(System.in);
        while (!triviaMaze.atGoal()) {
            System.out.println(triviaMaze.toString());
            System.out.println(triviaMaze.getPlayerLocation().getRow() + ", " + triviaMaze.getPlayerLocation().getCol());
            switch (s.nextLine()) {
                case "w" -> triviaMaze.move(NORTH);
                case "a" -> triviaMaze.move(WEST);
                case "s" -> triviaMaze.move(SOUTH);
                case "d" -> triviaMaze.move(EAST);
            }
        }
        System.out.println(triviaMaze.toString());
    }

    public static void main(String[] args) {
        game();
    }
}
