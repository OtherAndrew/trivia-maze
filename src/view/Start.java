package view;

import javax.swing.*;
import java.awt.*;

import static view.AppTheme.*;
import static view.FileAccessor.showDialog;
import static view.MazeDisplayBuilder.buildDummyMapDisplay;

public class Start extends JPanel {

    private final JPanel myMenuBar;
    private final JButton myNewGameBtn;
    private final JButton myResumeBtn;
    private final JButton myLoadGameBtn;
    private final JButton myAboutBtn;
    private final JButton myQuitBtn;

    Start(final Game theGame) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        adjustPanel(this);

        myNewGameBtn = buildButton("New Game");
        myResumeBtn = buildButton("Resume");
        myLoadGameBtn = buildButton("Load");
        myAboutBtn = buildButton("About");
        myQuitBtn = buildButton("Quit");

        myNewGameBtn.addActionListener(e -> theGame.show("difficulty"));
        myResumeBtn.addActionListener(e -> { if (theGame.getController().quickLoad())
            theGame.show("game");
        });
        myLoadGameBtn.addActionListener(e ->
                FileAccessor.getInstance().loadFile(this).ifPresent(theGame::load));
        myAboutBtn.addActionListener(e -> showDialog(this, "/about.txt",
                        "About Trivia Maze"));
        myQuitBtn.addActionListener(e -> System.exit(1));

        myMenuBar = buildMenubar(myNewGameBtn, myResumeBtn, myLoadGameBtn,
                myAboutBtn, myQuitBtn);

        add(myMenuBar, BorderLayout.NORTH);
        add(buildDummyMapDisplay(theGame.getController()), BorderLayout.CENTER);
    }
}
