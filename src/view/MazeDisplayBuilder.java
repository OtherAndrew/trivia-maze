package view;

import controller.TriviaMaze;

import javax.swing.*;
import java.awt.*;

import static model.mazecomponents.Symbol.*;
import static view.AppTheme.*;

/**
 * MazeDisplayBuilder is a helper class that provides the ability to
 * construct displays for mazes.
 */
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
     * @return a map with all paths visible.
     */
    public static JPanel buildDummyMapDisplay(final TriviaMaze theController) {
        return buildMapDisplay(theController.getDummyCharArray(), true);
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
            case UNDISCOVERED_SYMBOL, UNVISITED -> {
                if (theOmniscient) tile.setBackground(MID_GREY);
                else tile.setBackground(DARK_GREY);
            }
            case GOAL -> {
                if (theOmniscient) drawTile(tile, GREEN, "!");
                else tile.setBackground(DARK_GREY);
            }
            case PLAYER -> drawTile(tile, LIGHT_GREY, "[]");
            case PLAYER_AT_START -> drawTile(tile, ORANGE, "[]");
            case PLAYER_AT_GOAL -> drawTile(tile, GREEN, "[]");
            case LOCKED_SYMBOL -> drawTile(tile, RED, "X");
            case CLOSED_SYMBOL -> drawTile(tile, PURPLE, "?");
            case OPENED_SYMBOL, VISITED, PATH -> tile.setBackground(LIGHT_GREY);
            case WALL -> tile.setBackground(DARK_GREY);
            case START ->  tile.setBackground(ORANGE);
        }
        return tile;
    }

    /**
     * Adjusts a map tile's color and draws a label for it with the
     * specified text.
     *
     * @param theTile the tile being altered.
     * @param theColor the color to the set the tile.
     * @param theLabelText the text the label should display.
     */
    private static void drawTile(final JComponent theTile,
                                 final Color theColor,
                                 final String theLabelText) {
        theTile.setBackground(theColor);
        final JLabel label = new JLabel(theLabelText);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(BLACK);
        label.setFont(LARGE_FONT);
        theTile.add(label);
    }

    /**
     * Prevent instantiation.
     */
    private MazeDisplayBuilder() {}
}
