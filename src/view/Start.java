package view;

import javax.swing.*;
import java.awt.*;

import static view.AppTheme.*;
import static view.MazeDisplayBuilder.buildDummyMapDisplay;

public class Start {

    private final JPanel myPanel;
    private final JPanel myMenuBar;
    private final JButton myLoadGameBtn;
    private final JButton myNewGameBtn;
    private final JButton myQuitBtn;
    private final LoadGame myLoader;

    public Start(final JPanel thePanel, final CardLayout theCards) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        myPanel = buildPanel();

        myNewGameBtn = buildButton("New Game");
        myLoadGameBtn = buildButton("Load Game");
        myQuitBtn = buildButton("Quit");

        myLoader = new LoadGame();

        myNewGameBtn.addActionListener(theAction -> theCards.show(thePanel,
                "difficulty"));

        myLoadGameBtn.addActionListener(theAction -> myLoader.setFile());

        myQuitBtn.addActionListener(theAction -> System.exit(1));

        myMenuBar = buildMenubar(new JButton[]{myNewGameBtn, myLoadGameBtn,
                myQuitBtn});

        myPanel.add(myMenuBar, BorderLayout.NORTH);
        myPanel.add(buildDummyMapDisplay(6), BorderLayout.CENTER);

        myPanel.setVisible(true);
    }

    public JPanel getPanel() {
        return myPanel;
    }
}
