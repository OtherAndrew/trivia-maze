package view;

import javax.swing.*;
import java.awt.*;

/**
 * AppTheme defines the shared colors and fonts for every GUI class.
 * Colors are pulled from Dracula by Zeno Rocha
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

    /**
     * Empty constructor
     */
    private AppTheme() {}

    public static JButton buildJButton(final String theButtonText) {
        final JButton button = new JButton(theButtonText);
        button.setBackground(LIGHT_GREY);
        button.setForeground(BLACK);
        button.setFont(BUTTON_FONT);
        return button;
    }

    public static JRadioButton buildJRadioButton(final String theButtonText) {
        final JRadioButton radioButton = new JRadioButton(theButtonText);
        radioButton.setBackground(DARK_GREY);
        radioButton.setForeground(WHITE);
        radioButton.setFont(BUTTON_FONT);
        return radioButton;
    }
}
