package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * AppTheme defines the shared GUI elements for every GUI class.
 * Colors are pulled from the Dracula text editor color scheme by Zeno Rocha
 * (https://draculatheme.com/contribute).
 */
public final class AppTheme {
    public static final Color RED = Color.decode("#ff5555");
    public static final Color ORANGE = Color.decode("#ffb86c");
    public static final Color YELLOW = Color.decode("#f1fa8c");
    public static final Color GREEN = Color.decode("#50fa7b");
    public static final Color CYAN = Color.decode("#8be9fd");
    public static final Color PURPLE = Color.decode("#bd93f9");
    public static final Color PINK = Color.decode("#ff79c6");
    public static final Color WHITE = Color.decode("#f8f8f2");
    public static final Color LIGHT_GREY = Color.decode("#6272a4");
    public static final Color MID_GREY = Color.decode("#44475a");
    public static final Color DARK_GREY = Color.decode("#282a36");
    public static final Color BLACK = Color.decode("#101010");

    public static final Font BUTTON_FONT =
            new Font(Font.SANS_SERIF, Font.BOLD, 12);
    public static final Font TEXT_FONT =
            new Font(Font.SANS_SERIF, Font.BOLD, 15);
    public static final Font MAP_TILE_FONT =
            new Font(Font.SANS_SERIF, Font.BOLD, 18);

    public static final EmptyBorder GENERAL_BORDER = new EmptyBorder(10, 10, 10, 10);
    public static final EmptyBorder WINDOW_BORDER = new EmptyBorder(0, 10, 10, 10);
    public final static EmptyBorder MENUBAR_PADDING = new EmptyBorder(7, 0, 7, 0);
    public static final Dimension WINDOW_SIZE = new Dimension(720, 600);
    public static final String TITLE = "Trivia Maze";

    /**
     * Empty constructor
     */
    private AppTheme() {}

    /**
     * Builds a standard JFrame for each GUI class.
     *
     * @return the standard JFrame for each GUI class.
     */
    public static JFrame buildFrame() {
        final JFrame frame  = new JFrame(TITLE);
        frame.setLayout(new BorderLayout());
        frame.setSize(WINDOW_SIZE);
        frame.getRootPane().setBorder(WINDOW_BORDER);
        frame.setBackground(MID_GREY);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

    /**
     * Builds a standard JButton.
     *
     * @param theButtonText the text displayed on the button.
     * @return the standard JButton.
     */
    public static JButton buildButton(final String theButtonText) {
        final JButton button = new JButton(theButtonText);
        button.setBackground(LIGHT_GREY);
        button.setForeground(BLACK);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Builds a standard JRadioButton.
     *
     * @param theButtonText the text displayed on the radio button.
     * @return the standard JRadioButton.
     */
    public static JRadioButton buildRadioButton(final String theButtonText) {
        final JRadioButton radioButton = new JRadioButton(theButtonText);
        radioButton.setBackground(DARK_GREY);
        radioButton.setForeground(WHITE);
        radioButton.setFont(BUTTON_FONT);
        radioButton.setFocusPainted(false);
        return radioButton;
    }
}
