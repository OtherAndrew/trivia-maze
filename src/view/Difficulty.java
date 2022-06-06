package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static view.AppTheme.*;

public class Difficulty extends JPanel {

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

        myHelpButton = buildButton("Help");
        myKeyBindingsButton = buildButton("Key Bindings");
        myAboutButton = buildButton("About");
        myMainMenuButton = buildButton("Main Menu");

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

        final GridLayout difficultyLayout = new GridLayout(5, 1);
        difficultyLayout.setVgap(7);
        myDifficultyPanel = new JPanel(difficultyLayout);
        myDifficultyPanel.setBackground(MID_GREY);
        myDifficultyPanel.setBorder(new EmptyBorder(100, 100, 100, 100));

        myCheatPanel = new JPanel(new GridLayout(1, 2));
        myCheatPanel.add(myMasterKeyCheck);
        myCheatPanel.add(myXRayCheck);
        myCheatPanel.setBackground(DARK_GREY);
        myCheatPanel.setBorder(GENERAL_BORDER);

        myMenubar = buildMenubar(myHelpButton, myKeyBindingsButton, myAboutButton, myMainMenuButton);

        myHelpButton.addActionListener(e -> {
            try {
                final String helpText = Files.readString(Path.of(
                        "resources/help.txt"));
                JOptionPane.showMessageDialog(this,
                        helpText, "Help",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        myKeyBindingsButton.addActionListener(e -> {
            try {
                final String helpText = Files.readString(Path.of(
                        "resources/key_bindings.txt"));
                JOptionPane.showMessageDialog(this,
                        helpText, "Key Bindings",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        myAboutButton.addActionListener(e -> {
            try {
                final String aboutText = Files.readString(Path.of(
                        "resources/about.txt"));
                JOptionPane.showMessageDialog(this,
                        aboutText, "About Trivia Maze",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        );
        myMainMenuButton.addActionListener(e -> theCards.show(theGame.getContentPanel(), "start"));

        myMasterKeyCheck.addActionListener(e -> myMasterKey = myMasterKeyCheck.isSelected());

        myXRayCheck.addActionListener(e -> myXRay = myXRayCheck.isSelected());

        int dim = 4;
        for (JButton button : new JButton[]{myEasyButton, myMediumButton, myHardButton, myInsaneButton}) {
            int finalDim = dim;
            button.addActionListener(e -> {
                theGame.getController().buildMaze(finalDim, finalDim, myMasterKey, myXRay);
                theGame.updateMapDisplay(myXRay);
                theGame.updateQA();
                theCards.show(theGame.getContentPanel(), "game");
            });
            dim += 2;
            myDifficultyPanel.add(button);
        }
        myDifficultyPanel.add(myCheatPanel);

        add(myMenubar, BorderLayout.NORTH);
        add(myDifficultyPanel, BorderLayout.CENTER);

        // TODO: dev mode button/buttons (omniscient map, always open doors,
        //  highlighted answers, etc)
    }
}
