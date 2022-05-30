package controller;

import model.Maze;
import model.mazecomponents.Door;
import model.mazecomponents.Room;
import model.mazecomponents.State;
import model.questions.Question;

import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

import static model.mazecomponents.Direction.*;
import static model.mazecomponents.State.*;

public class SkeletonGame {

    public static void game() {
        Random r = new Random();
        final int dim = r.nextInt(7) + 3;
        Maze triviaMaze = new Maze(dim, dim);
        Scanner s = new Scanner(System.in);
        System.out.println("Use WASD to move.");
//        System.out.println("Lock doors with q.");
//        System.out.println("Unlock doors with e.");
        triviaMaze.setAllDoors(OPEN);
        System.out.println();
        while (!triviaMaze.atGoal() && !triviaMaze.gameLoss()) {
//            System.out.println(triviaMaze.toString());
            System.out.println(triviaMaze.playerRoomToString());
            Room playerLocation = triviaMaze.getPlayerLocation();
            System.out.println("Position: "
                    + playerLocation.getRow()
                    + ", "
                    + playerLocation.getCol());
            switch (s.nextLine()) {
                case "w" -> triviaMaze.attemptMove(NORTH);
                case "a" -> triviaMaze.attemptMove(WEST);
                case "s" -> triviaMaze.attemptMove(SOUTH);
                case "d" -> triviaMaze.attemptMove(EAST);
            }
//            if (d.isPresent()) {
//                Door door = d.get();
//                Question q = triviaMaze.getQuestion(door);
//                System.out.println(q);
//                String ans = s.nextLine();
////                triviaMaze.changeDoorState(door, q, ans);
//                switch (ans) {
//                    case "q" -> {
//                        door.setState(LOCKED);
//                        System.out.println("*** Locked. ***");
//                    }
//                    case "e" -> {
//                        door.setState(State.OPEN);
//                        System.out.println("*** Opened. ***");
//                        triviaMaze.move(door.getDirection(playerLocation));
//                    }
//                }
//            }
        }
        System.out.println(triviaMaze);
        if (triviaMaze.gameLoss()) {
            System.out.println("*** Game lost. ***");
        }
        if (triviaMaze.atGoal()) {
            System.out.println("*** Game won. ***");
        }
        System.out.println("Rooms visited: " + triviaMaze.getRoomVisitedNum());
        System.out.println("Doors opened: " + triviaMaze.getDoorStateNum(OPEN));
        System.out.println("Closed doors: " + triviaMaze.getDoorStateNum(CLOSED));
        System.out.println("Locked doors: " + triviaMaze.getDoorStateNum(LOCKED));
    }

    public static void main(String[] args) {
        game();
    }
}