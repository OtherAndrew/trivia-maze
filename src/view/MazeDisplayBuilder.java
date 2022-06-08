package view;

import controller.TriviaMaze;

import javax.swing.*;
import java.awt.*;

import static model.mazecomponents.Symbol.*;
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
            case PATH -> tile.setBackground(BLUE);
            case UNDISCOVERED_SYMBOL, UNVISITED -> {
                if (theOmniscient) tile.setBackground(MID_GREY);
                else tile.setBackground(DARK_GREY);
            }
            case GOAL -> {
                if (theOmniscient) {
                    tile.setBackground(GREEN);
                    tile.add(drawLabel("!"), BorderLayout.CENTER);
                }
                else tile.setBackground(DARK_GREY);
            }
            case PLAYER -> {
                tile.setBackground(LIGHT_GREY);
                tile.add(drawLabel("[]"), BorderLayout.CENTER);
            }
            case PLAYER_AT_START -> {
                tile.setBackground(ORANGE);
                tile.add(drawLabel("[]"), BorderLayout.CENTER);
            }
            case PLAYER_AT_GOAL -> {
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
            case OPENED_SYMBOL, VISITED -> tile.setBackground(LIGHT_GREY);
            case WALL -> tile.setBackground(DARK_GREY);
            case START ->  tile.setBackground(ORANGE);
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
