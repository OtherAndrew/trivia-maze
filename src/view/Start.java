package view;

import javax.swing.*;
import java.awt.*;

import static view.AppTheme.*;
//import static view.MazeDisplayBuilder.buildDummyMapDisplay;

public class Start extends JPanel {

    private final JPanel myMenuBar;
    private final JButton myLoadGameBtn;
    private final JButton myNewGameBtn;
    private final JButton myQuitBtn;

    public Start(final JPanel thePanel, final CardLayout theCards) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        adjustPanel(this);

        myNewGameBtn = buildButton("New Game");
        myLoadGameBtn = buildButton("Load Game");
        myQuitBtn = buildButton("Quit");

        myNewGameBtn.addActionListener(theAction -> theCards.show(thePanel, "difficulty"));

        myLoadGameBtn.addActionListener(theAction -> FileAccessor.getInstance().loadFile());

        myQuitBtn.addActionListener(theAction -> System.exit(1));

        myMenuBar = buildMenubar(myNewGameBtn, myLoadGameBtn, myQuitBtn);

        add(myMenuBar, BorderLayout.NORTH);
        // add(buildDummyMapDisplay(6), BorderLayout.CENTER);
    }
}
