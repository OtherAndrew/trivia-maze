package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Game {

    private JFrame myFrame;
    private JButton myRoomButton, myMapButton;
    private final String myTitle = "Trivia Maze", myWindowIconPath = "assets\\Landing_page_01.png";
    private final Dimension myPreferredSize = new Dimension(1000,1000);

    public Game() {
        myFrame = new JFrame(myTitle);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(new GridLayout(10, 10));
        myFrame.setSize(myPreferredSize);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);

        myRoomButton = new JButton("Room");
        myMapButton = new JButton("Map");
        myRoomButton.addActionListener(theAction -> {
            new RoomDisplay();
            myFrame.dispose();
        });
        myMapButton.addActionListener(theAction -> {
            new MapDisplay();
            myFrame.dispose();
        });
        myFrame.add(myRoomButton);
        myFrame.add(myMapButton);
//        for (int row = 0; row < maze.length; row++) {
//            for (int col = 0; col < maze[0].length; col++) {
//                JButton tile = makeTile(maze[row][col]);
//                mainFrame.add(tile);
//            }
//        }
    }

//    private JButton makeTile() {
//
//    }
    public static void main(String[] args) {
        Game game = new Game();
    }
}
