package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static view.AppTheme.*;
import static view.FileAccessor.showDialog;

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
    private final JCheckBox myMasterKeyCheck;
    private final JCheckBox myXRayCheck;

    private boolean myXRay;

    Difficulty(final Game theGame) {
        myXRay = false;

        System.setProperty("awt.useSystemAAFontSettings", "on");
        adjustPanel(this);

        myMainMenuButton = buildButton("Main Menu");
        myHelpButton = buildButton("Help");
        myKeyBindingsButton = buildButton("Key Bindings");
        myAboutButton = buildButton("About");
        myHelpButton.addActionListener(e -> showDialog(this,"/help.txt",
                "Help"));
        myKeyBindingsButton.addActionListener(e -> showDialog(this,
                "/key_bindings.txt", "Key Bindings"));
        myAboutButton.addActionListener(e -> showDialog(this, "/about.txt",
                "About Trivia Maze"));
        myMainMenuButton.addActionListener(e -> theGame.show("start"));
        myMenubar = buildMenubar(myMainMenuButton, myHelpButton,
                myKeyBindingsButton, myAboutButton);

        final GridLayout difficultyLayout = new GridLayout(5, 1);
        difficultyLayout.setVgap(7);
        myDifficultyPanel = new JPanel(difficultyLayout);
        myDifficultyPanel.setBackground(MID_GREY);
        myDifficultyPanel.setBorder(DIFFICULTY_BUTTON_BORDER);
        makeDifficultyButton(4, GREEN, theGame);
        makeDifficultyButton(6, ORANGE, theGame);
        makeDifficultyButton(8, RED, theGame);
        makeDifficultyButton(10, PINK, theGame);

        myCheatPanel = new JPanel(new GridLayout(1, 2));
        myCheatPanel.setBackground(DARK_GREY);
        myCheatPanel.setBorder(GENERAL_BORDER);
        myMasterKeyCheck = buildCheckBox("Master Key");
        myXRayCheck = buildCheckBox("X-Ray");
        myMasterKeyCheck.addActionListener(e ->
                theGame.getController().setMasterKeyEnabled(myMasterKeyCheck.isSelected()));
        myXRayCheck.addActionListener(e -> {
            myXRay = myXRayCheck.isSelected();
            theGame.getController().setXRayEnabled(myXRay);
        });
        myCheatPanel.add(myMasterKeyCheck);
        myCheatPanel.add(myXRayCheck);
        myDifficultyPanel.add(myCheatPanel);

        add(myMenubar, BorderLayout.NORTH);
        add(myDifficultyPanel, BorderLayout.CENTER);
    }

    private void makeDifficultyButton(final int theDim,
                                      final Color theColor,
                                      final Game theGame) {
        final JButton button = buildButton(theDim + " x " + theDim);
        button.setBackground(theColor);
        button.setFont(LARGE_FONT);
        button.addActionListener(e -> {
            theGame.getController().buildMaze(theDim, theDim);
            theGame.updateMapDisplay(myXRay);
            theGame.show("game");
        });
        myDifficultyPanel.add(button);
    }
}
