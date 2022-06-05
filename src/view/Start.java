package view;

import javax.swing.*;
import java.awt.*;

import static view.AppTheme.*;
import static view.MazeDisplayBuilder.buildDummyMapDisplay;

public class Start extends JPanel {

    private final JPanel myMenuBar;
    private final JButton myLoadGameBtn;
    private final JButton myNewGameBtn;
    private final JButton myQuitBtn;

    Start(final Game theGame, final CardLayout theCards) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        adjustPanel(this);

        myNewGameBtn = buildButton("New Game");
        myLoadGameBtn = buildButton("Load Game");
        myQuitBtn = buildButton("Quit");

        myNewGameBtn.addActionListener(e -> theCards.show(theGame.getContentPanel(), "difficulty"));

        myLoadGameBtn.addActionListener(e -> FileAccessor.getInstance().loadFile().ifPresent(file -> {
            theGame.getController().load(file);
            theGame.updateQA();
            theCards.show(theGame.getContentPanel(), "game");
        }));

        myQuitBtn.addActionListener(e -> System.exit(1));

        myMenuBar = buildMenubar(myNewGameBtn, myLoadGameBtn, myQuitBtn);

        add(myMenuBar, BorderLayout.NORTH);
        add(buildDummyMapDisplay(theGame.getController()), BorderLayout.CENTER);
    }
}
