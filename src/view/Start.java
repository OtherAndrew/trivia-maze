package view;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.StringJoiner;

import static view.AppTheme.*;
import static view.MazeDisplayBuilder.buildDummyMapDisplay;

public class Start extends JPanel {

    private final JPanel myMenuBar;
    private final JButton myNewGameBtn;
    private final JButton myResumeBtn;
    private final JButton myLoadGameBtn;
    private final JButton myAboutBtn;
    private final JButton myQuitBtn;

    Start(final Game theGame, final CardLayout theCards) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        adjustPanel(this);

        myNewGameBtn = buildButton("New Game");
        myResumeBtn = buildButton("Resume");
        myLoadGameBtn = buildButton("Load");
        myAboutBtn = buildButton("About");
        myQuitBtn = buildButton("Quit");

        myNewGameBtn.addActionListener(e -> theCards.show(theGame.getContentPanel(), "difficulty"));
        myResumeBtn.addActionListener(e -> {
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
        myAboutBtn.addActionListener(e -> {
            try {
                final InputStream in = getClass()
                        .getResourceAsStream("/about.txt");
                final BufferedReader lines =
                        new BufferedReader(new InputStreamReader(Objects.requireNonNull(in)));
                final StringJoiner content = new StringJoiner("\n");
                String line;
                while ((line = lines.readLine()) != null) {
                    content.add(line);
                }
                JOptionPane.showMessageDialog(this,
                        content.toString(), "About Trivia Maze",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        myQuitBtn.addActionListener(e -> System.exit(1));

        myMenuBar = buildMenubar(myNewGameBtn, myResumeBtn,
                myLoadGameBtn, myAboutBtn, myQuitBtn);

        add(myMenuBar, BorderLayout.NORTH);
        add(buildDummyMapDisplay(theGame.getController()), BorderLayout.CENTER);
    }
}
