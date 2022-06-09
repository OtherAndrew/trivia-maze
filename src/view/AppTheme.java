package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * AppTheme defines the shared GUI elements for every GUI class.
 * Colors are pulled from the Dracula text editor color scheme by Zeno Rocha
 * (<a href="https://draculatheme.com/contribute">...</a>).
 */
public final class AppTheme {
    public static final Color RED = Color.decode("#ff5555");
    public static final Color ORANGE = Color.decode("#ffb86c");
    public static final Color YELLOW = Color.decode("#f1fa8c");
    public static final Color GREEN = Color.decode("#50fa7b");
    public static final Color BLUE = Color.decode("#8be9fd");
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
    public static final Font LARGE_FONT =
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

    public static void adjustPanel(final JPanel thePanel) {
        thePanel.setLayout(new BorderLayout());
        thePanel.setSize(WINDOW_SIZE);
        thePanel.setBorder(WINDOW_BORDER);
        thePanel.setBackground(MID_GREY);
    }

    public static JPanel buildPanel() {
        final JPanel panel  = new JPanel();
        adjustPanel(panel);
        return panel;
    }

    private static void buildGeneric(final AbstractButton theButton,
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
        buildGeneric(button, LIGHT_GREY, BLACK);
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
        buildGeneric(radioButton, DARK_GREY, WHITE);
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
        buildGeneric(checkbox, DARK_GREY, WHITE);
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
