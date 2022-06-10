package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static java.awt.Color.decode;
import static java.awt.Font.BOLD;
import static java.awt.Font.SANS_SERIF;

/**
 * AppTheme defines the shared GUI elements for every GUI class.
 * Colors are pulled from the Dracula text editor color scheme by Zeno Rocha
 * (<a href="https://draculatheme.com/contribute">...</a>).
 */
public final class AppTheme {

    /**
     * Red color.
     */
    public static final Color RED = decode("#ff5555");
    /**
     * Orange color.
     */
    public static final Color ORANGE = decode("#ffb86c");
    /**
     * Yellow color.
     */
    public static final Color YELLOW = decode("#f1fa8c");
    /**
     * Green color.
     */
    public static final Color GREEN = decode("#50fa7b");
    /**
     * Blue color.
     */
    public static final Color BLUE = decode("#8be9fd");
    /**
     * Purple color.
     */
    public static final Color PURPLE = decode("#bd93f9");
    /**
     * Pink color.
     */
    public static final Color PINK = decode("#ff79c6");
    /**
     * White color.
     */
    public static final Color WHITE = decode("#f8f8f2");
    /**
     * Light grey color.
     */
    public static final Color LIGHT_GREY = decode("#6272a4");
    /**
     * Medium grey color.
     */
    public static final Color MID_GREY = decode("#44475a");
    /**
     * Dark grey color.
     */
    public static final Color DARK_GREY = decode("#282a36");
    /**
     * Black color.
     */
    public static final Color BLACK = decode("#101010");

    /**
     * Font for buttons.
     */
    public static final Font BUTTON_FONT = new Font(SANS_SERIF, BOLD, 12);
    /**
     * Font for text.
     */
    public static final Font TEXT_FONT = new Font(SANS_SERIF, BOLD, 15);
    /**
     * Big font.
     */
    public static final Font LARGE_FONT = new Font(SANS_SERIF, BOLD, 18);

    /**
     * General border.
     */
    public static final EmptyBorder GENERAL_BORDER = new EmptyBorder(10, 10, 10, 10);
    /**
     * Border for window.
     */
    public static final EmptyBorder WINDOW_BORDER = new EmptyBorder(0, 10, 10, 10);
    /**
     * Border for menu-bars.
     */
    public final static EmptyBorder MENUBAR_PADDING = new EmptyBorder(7, 0, 7, 0);
    /**
     * Default and fixed dimension for window.
     */
    public static final Dimension WINDOW_SIZE = new Dimension(720, 600);
    /**
     * Default title for window.
     */
    public static final String TITLE = "Trivia Maze";

    /**
     * Prevent instantiation.
     */
    private AppTheme() {}

    /**
     * Builds a standard JFrame.
     *
     * @return the standard JFrame.
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
     * Takes a panel and performs adjustments to it.
     *
     * @param thePanel  the panel to adjust.
     */
    public static void adjustPanel(final JPanel thePanel) {
        thePanel.setLayout(new BorderLayout());
        thePanel.setSize(WINDOW_SIZE);
        thePanel.setBorder(WINDOW_BORDER);
        thePanel.setBackground(MID_GREY);
    }

    /**
     * Builds a standard JPanel.
     *
     * @return the standard JPanel.
     */
    public static JPanel buildPanel() {
        final JPanel panel  = new JPanel();
        adjustPanel(panel);
        return panel;
    }

    /**
     * Takes a button and performs adjustments to it.
     *
     * @param theButton     the button to adjust.
     * @param theBackColor  the color to set the background.
     * @param theForeColor  the color to set the foreground.
     */
    private static void adjustButton(final AbstractButton theButton,
                                     final Color theBackColor,
                                     final Color theForeColor) {
        theButton.setBackground(theBackColor);
        theButton.setForeground(theForeColor);
        theButton.setFont(BUTTON_FONT);
        theButton.setFocusPainted(false);
    }

    /**
     * Builds a standard JButton.
     *
     * @param theButtonText the text displayed on the button.
     * @return the standard JButton.
     */
    public static JButton buildButton(final String theButtonText) {
        final JButton button = new JButton(theButtonText);
        adjustButton(button, LIGHT_GREY, BLACK);
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
        adjustButton(radioButton, DARK_GREY, WHITE);
        return radioButton;
    }

    /**
     * Builds a standard JCheckBox.
     *
     * @param theButtonText the text displayed on the checkbox.
     * @return the standard JCheckBox.
     */
    public static JCheckBox buildCheckBox(final String theButtonText) {
        final JCheckBox checkbox = new JCheckBox(theButtonText);
        adjustButton(checkbox, DARK_GREY, WHITE);
        return checkbox;
    }

    /**
     * Builds a standard menubar.
     *
     * @param theButtons the buttons to include in the menubar.
     * @return a standard menubar.
     */
    public static JPanel buildMenubar(final JComponent... theButtons) {
        final JPanel menubar = new JPanel(new GridLayout(1, theButtons.length));
        menubar.setBackground(MID_GREY);
        for (JComponent button : theButtons) {
            menubar.add(button);
        }
        menubar.setBorder(MENUBAR_PADDING);
        return menubar;
    }

    /**
     * Builds a buffer panel.
     */
    public static JPanel buildBufferPanel() {
        final JPanel buffer = new JPanel();
        buffer.setBackground(MID_GREY);
        return buffer;
    }
}
