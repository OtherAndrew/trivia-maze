package view;

import javax.swing.*;
import java.awt.*;

import static view.AppTheme.*;
import static view.MazeDisplayBuilder.buildDummyMapDisplay;

/**
 * Start is the starting screen for the application and allows the user to
 * start a new game or continue an old one.
 */
public class Start extends JPanel {

    /**
     * The menubar to navigate the starting screen options.
     */
    private final JPanel myMenuBar;
    /**
     * Starts a new game.
     */
    private final JButton myNewGameBtn;
    /**
     * Quick load a game.
     */
    private final JButton myResumeBtn;
    /**
     * Load a user-selected game.
     */
    private final JButton myLoadGameBtn;
    /**
     * Information about the developers.
     */
    private final JButton myAboutBtn;
    /**
     * Exit the game.
     */
    private final JButton myQuitBtn;

    /**
     * Creates a start.
     *
     * @param theGame   the main GUI for the gameplay.
     */
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
                FileAccessor.getFileAccessor().loadFile(this).ifPresent(theGame::load));
        myAboutBtn.addActionListener(e -> FileAccessor.showResource(this,
                "/about.txt", "About Trivia Maze"));
        myQuitBtn.addActionListener(e -> System.exit(1));

        myMenuBar = buildMenubar(myNewGameBtn, myResumeBtn, myLoadGameBtn,
                myAboutBtn, myQuitBtn);

        add(myMenuBar, BorderLayout.NORTH);
        add(buildDummyMapDisplay(theGame.getController()), BorderLayout.CENTER);
    }
}
