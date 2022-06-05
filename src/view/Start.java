package view;

import javax.swing.*;
import java.awt.*;

import static view.AppTheme.*;
import static view.MazeDisplayBuilder.buildDummyMapDisplay;

public class Start extends JPanel {

    private final JPanel myMenuBar;
    private final JButton myNewGameBtn;
    private final JButton myQuickLoadGameBtn;
    private final JButton myLoadGameBtn;
    private final JButton myHelpBtn;
    private final JButton myQuitBtn;

    Start(final Game theGame, final CardLayout theCards) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        adjustPanel(this);

        myNewGameBtn = buildButton("New Game");
        myQuickLoadGameBtn = buildButton("Quick Load");
        myLoadGameBtn = buildButton("Load");
        myHelpBtn = buildButton("Help");
        myQuitBtn = buildButton("Quit");

        myNewGameBtn.addActionListener(e -> theCards.show(theGame.getContentPanel(), "difficulty"));
        myQuickLoadGameBtn.addActionListener(e -> {
            if (theGame.getController().quickLoad()) {
                theGame.updateQA();
                theCards.show(theGame.getContentPanel(), "game");
            }
        });
        myLoadGameBtn.addActionListener(e -> FileAccessor.getInstance().loadFile().ifPresent(file -> {
            theGame.getController().load(file);
            theGame.updateQA();
            theCards.show(theGame.getContentPanel(), "game");
        }));
        myHelpBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "PLACEHOLDER");
        });
        myQuitBtn.addActionListener(e -> System.exit(1));

        myMenuBar = buildMenubar(myNewGameBtn, myQuickLoadGameBtn,
                myLoadGameBtn, myHelpBtn, myQuitBtn);

        add(myMenuBar, BorderLayout.NORTH);
        add(buildDummyMapDisplay(theGame.getController()), BorderLayout.CENTER);
    }
}
