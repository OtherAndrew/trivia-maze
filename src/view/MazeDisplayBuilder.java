package view;

import model.Maze;
import model.mazecomponents.State;

import javax.swing.*;
import java.awt.*;

import static model.Maze.*;
import static model.Maze.START_SYMBOL;
import static model.mazecomponents.Door.*;
import static model.mazecomponents.Door.OPEN_SYMBOL;
import static model.mazecomponents.Room.UNVISITED_SYMBOL;
import static model.mazecomponents.Room.VISITED_SYMBOL;
import static view.AppTheme.*;


public final class MazeDisplayBuilder {



    /**
     * Draws a map display from the character array representation of one or
     * more rooms.
     *
     * @param theCharArray  a character array representing one or more rooms
     *                      in the maze.
     * @param theOmniscient if the output should display an omniscient view.
     * @return a JPanel that displays the character array as a series of tiles.
     */
    public static JPanel buildMapDisplay(final char[][] theCharArray,
                                         final boolean theOmniscient) {
        final JPanel mapDisplay =
                new JPanel(new GridLayout(theCharArray.length,
                        theCharArray[0].length));
        for (char[] row : theCharArray) {
            for (char space : row) {
                mapDisplay.add(buildTile(space, theOmniscient));
            }
        }
        mapDisplay.setBackground(MID_GREY);
        return mapDisplay;
    }

    /**
     * Draws a map display showing the discovered rooms and doors.
     *
     * @param theCharArray a character array representing one or more rooms
     *                     in the maze.
     * @return a JPanel that displays the character array as a series of tiles.
     */
    public static JPanel buildMapDisplay(final char[][] theCharArray) {
        return buildMapDisplay(theCharArray,false);
    }

    /**
     * Draws a map display for display purposes only.
     *
     * @param theSize the x and y size of the maze.
     * @return a map with all paths visible.
     */
    public static JPanel buildDummyMapDisplay(final int theSize) {
        final Maze dummyMaze = new Maze(theSize, theSize);
        dummyMaze.setAllDoors(State.UNDISCOVERED);
        return buildMapDisplay(dummyMaze.toCharArray(), true);
    }

    /**
     * Returns a tile based on the given parameters.
     *
     * @param theChar       the character representation of the tile.
     * @param theOmniscient if an omniscient view is desired.
     * @return a maze tile.
     */
    private static JComponent buildTile(final char theChar,
                                        final boolean theOmniscient) {
        final JComponent tile = new JPanel(new BorderLayout());
        switch (theChar) {
            case UNDISCOVERED_SYMBOL, UNVISITED_SYMBOL -> {
                if (theOmniscient) tile.setBackground(MID_GREY);
                else tile.setBackground(DARK_GREY);
            }
            case GOAL -> {
                if (theOmniscient) tile.setBackground(GREEN);
                else tile.setBackground(DARK_GREY);
            }
            case PLAYER_SYMBOL -> {
                tile.setBackground(LIGHT_GREY);
                tile.add(drawLabel("[]"), BorderLayout.CENTER);
            }
            case PLAYER_AT_START_SYMBOL -> {
                tile.setBackground(ORANGE);
                tile.add(drawLabel("[]"), BorderLayout.CENTER);
            }
            case PLAYER_AT_GOAL_SYMBOL -> {
                tile.setBackground(GREEN);
                tile.add(drawLabel("[]"), BorderLayout.CENTER);
            }
            case LOCKED_SYMBOL -> {
                tile.setBackground(RED);
                tile.add(drawLabel("X"), BorderLayout.CENTER);
            }
            case CLOSED_SYMBOL -> {
                tile.setBackground(PURPLE);
                tile.add(drawLabel("?"), BorderLayout.CENTER);
            }
            case OPEN_SYMBOL, VISITED_SYMBOL -> tile.setBackground(LIGHT_GREY);
            case WALL_SYMBOL -> tile.setBackground(DARK_GREY);
            case START_SYMBOL ->  tile.setBackground(ORANGE);
        }
        return tile;
    }

    /**
     * Draws a label for a map tile with the specified text.
     *
     * @param theLabelText the text the label should display.
     * @return a label with the specified text.
     */
    private static JLabel drawLabel(final String theLabelText) {
        final JLabel output = new JLabel(theLabelText);
        output.setHorizontalAlignment(SwingConstants.CENTER);
        output.setForeground(BLACK);
        output.setFont(LARGE_FONT);
        return output;
    }

    /**
     * Empty constructor
     */
    private MazeDisplayBuilder() {}
}
