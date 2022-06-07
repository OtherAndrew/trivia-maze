package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

import static view.AppTheme.*;

public class Difficulty extends JPanel {

    public static final EmptyBorder DIFFICULTY_BUTTON_BORDER =
            new EmptyBorder(100, 100, 100, 100);

    private final JPanel myMenubar;
    private final JPanel myDifficultyPanel;
    private final JPanel myCheatPanel;
    private final JButton myHelpButton;
    private final JButton myKeyBindingsButton;
    private final JButton myAboutButton;
    private final JButton myMainMenuButton;
    private final JButton myEasyButton;
    private final JButton myMediumButton;
    private final JButton myHardButton;
    private final JButton myInsaneButton;
    private final JCheckBox myMasterKeyCheck;
    private final JCheckBox myXRayCheck;

    private boolean myMasterKey;
    private boolean myXRay;

    Difficulty(final Game theGame, final CardLayout theCards) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        adjustPanel(this);
        myMasterKey = false;
        myXRay = false;

        myMainMenuButton = buildButton("Main Menu");
        myHelpButton = buildButton("Help");
        myKeyBindingsButton = buildButton("Key Bindings");
        myAboutButton = buildButton("About");

        myEasyButton = buildButton("4 x 4");
        myEasyButton.setBackground(GREEN);
        myEasyButton.setFont(LARGE_FONT);
        myMediumButton = buildButton("6 x 6");
        myMediumButton.setBackground(ORANGE);
        myMediumButton.setFont(LARGE_FONT);
        myHardButton = buildButton("8 x 8");
        myHardButton.setBackground(RED);
        myHardButton.setFont(LARGE_FONT);
        myInsaneButton = buildButton("10 x 10");
        myInsaneButton.setBackground(PINK);
        myInsaneButton.setFont(LARGE_FONT);

        myMasterKeyCheck = buildCheckBox("Master Key");
        myXRayCheck = buildCheckBox("X-Ray");

        final GridLayout difficultyLayout = new GridLayout(4, 1);
        difficultyLayout.setVgap(7);
        myDifficultyPanel = new JPanel(difficultyLayout);
        myDifficultyPanel.setBackground(MID_GREY);
        myDifficultyPanel.setBorder(DIFFICULTY_BUTTON_BORDER);

        myCheatPanel = new JPanel(new FlowLayout());
        myCheatPanel.add(myMasterKeyCheck);
        myCheatPanel.add(myXRayCheck);
        myCheatPanel.setBackground(DARK_GREY);
        myCheatPanel.setBorder(GENERAL_BORDER);

        myMenubar = buildMenubar(myMainMenuButton, myHelpButton,
                myKeyBindingsButton, myAboutButton);

        myHelpButton.addActionListener(e -> showDialog("/help.txt", "Help"));
        myKeyBindingsButton.addActionListener(e -> showDialog("/key_bindings.txt",
                "Key Bindings"));
        myAboutButton.addActionListener(e -> showDialog("/about.txt",
                "About Trivia Maze"));
        myMainMenuButton.addActionListener(e -> theCards.show(theGame.getContentPanel(), "start"));

        myMasterKeyCheck.addActionListener(e -> {
            myMasterKey = myMasterKeyCheck.isSelected();
            theGame.getController().setMasterKeyEnabled(myMasterKey);
        });
        myXRayCheck.addActionListener(e -> {
            myXRay = myXRayCheck.isSelected();
            theGame.getController().setXRayEnabled(myXRay);
        });

        int dim = 4;
        for (JButton button : new JButton[]{myEasyButton, myMediumButton,
                myHardButton, myInsaneButton}) {
            int finalDim = dim;
            button.addActionListener(e -> {
                theGame.getController().buildMaze(finalDim, finalDim);
                theGame.updateMapDisplay(myXRay);
                theGame.updateQA();
                theCards.show(theGame.getContentPanel(), "game");
            });
            dim += 2;
            myDifficultyPanel.add(button);
        }

        add(myMenubar, BorderLayout.NORTH);
        add(myDifficultyPanel, BorderLayout.CENTER);
        add(myCheatPanel, BorderLayout.SOUTH);
    }

    private void showDialog(final String theFilePath, final String theTitle) {
        try (final InputStream in = getClass().getResourceAsStream(theFilePath);
             final BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(in)))) {
            JOptionPane.showMessageDialog(this,
                    br.lines().collect(Collectors.joining("\n")), theTitle,
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
}
