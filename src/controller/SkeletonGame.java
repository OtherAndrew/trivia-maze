package controller;

import model.Maze;
import model.mazecomponents.Door;
import model.mazecomponents.Room;
import model.mazecomponents.State;
import model.questions.Question;

import java.util.Optional;
import java.util.Scanner;

import static model.mazecomponents.Direction.*;
import static model.mazecomponents.State.*;

public class SkeletonGame {

    public static void game() {
        Maze triviaMaze = new Maze(7, 7);
        Scanner s = new Scanner(System.in);
        Optional<Door> d = Optional.empty();
        System.out.println("Use WASD to move.");
        System.out.println("Lock doors with q.");
        System.out.println("Unlock doors with e.");
        System.out.println();
        while (!triviaMaze.atGoal() && !triviaMaze.gameLoss()) {
            System.out.println(triviaMaze);
            Room playerLocation = triviaMaze.getPlayerLocation();
            System.out.println("Position: "
                    + playerLocation.getRow()
                    + ", "
                    + playerLocation.getCol());
            System.out.println("Visited rooms: " + triviaMaze.getNumVisited());
//            System.out.println("Locked doors: " + triviaMaze.getStateNum(LOCKED));
//            System.out.println("Closed doors: " + triviaMaze.getStateNum(CLOSED));
//            System.out.println("Open doors: " + triviaMaze.getStateNum(OPEN));
            switch (s.nextLine()) {
                case "w" -> d = triviaMaze.move(NORTH);
                case "a" -> d = triviaMaze.move(WEST);
                case "s" -> d = triviaMaze.move(SOUTH);
                case "d" -> d = triviaMaze.move(EAST);
            }
            if (d.isPresent()) {
                Door door = d.get();
                Question q = triviaMaze.getQuestion(door);
                System.out.println(q);
                String ans = s.nextLine();
//                triviaMaze.changeDoorState(door, q, ans);
                switch (ans) {
                    case "q" -> {
                        door.setState(LOCKED);
                        System.out.println("*** Locked. ***");
                    }
                    case "e" -> {
                        door.setState(State.OPEN);
                        System.out.println("*** Opened. ***");
                        triviaMaze.move(door.getDirection(playerLocation));
                    }
                }
            }
        }
        System.out.println(triviaMaze);
        if (triviaMaze.gameLoss()) {
            System.out.println("*** Game lost. ***");
        }
        if (triviaMaze.atGoal()) {
            System.out.println("*** Game won. ***");
        }
    }

    public static void main(String[] args) {
        game();
    }
}
