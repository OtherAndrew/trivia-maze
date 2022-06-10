package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static view.AppTheme.*;
import static view.FileAccessor.showResource;

/**
 * Difficulty is a screen where the user can pick a difficult to start a new
 * game as well as toggle on cheats.
 */
public class Difficulty extends JPanel {

    /**
     * Border of difficulty buttons.
     */
    public static final EmptyBorder DIFFICULTY_BUTTON_BORDER =
            new EmptyBorder(100, 100, 100, 100);

    /**
     * The menubar to navigate the difficulty select screen options.
     */
    private final JPanel myMenubar;
    /**
     * Panel to contain difficulty buttons.
     */
    private final JPanel myDifficultyPanel;
    /**
     * Panel to contain cheat toggles.
     */
    private final JPanel myCheatPanel;
    /**
     * Brings up the rules of the game.
     */
    private final JButton myHelpButton;
    /**
     * Brings up the keyboard shortcuts usable in the game.
     */
    private final JButton myKeyBindingsButton;
    /**
     * Information about the developers.
     */
    private final JButton myAboutButton;
    /**
     * Swaps back to the start screen.
     */
    private final JButton myMainMenuButton;
    /**
     * Toggle for whether the master key cheat is active.
     */
    private final JCheckBox myMasterKeyCheck;
    /**
     * Toggle for whether the x-ray cheat is active.
     */
    private final JCheckBox myXRayCheck;
    /**
     * Keeps track of whether the x-ray cheat is active.
     */
    private boolean myXRay;

    /**
     * Creates a difficulty select screen.
     *
     * @param theGame   the main GUI for the gameplay.
     */
    Difficulty(final Game theGame) {
        myXRay = false;

        System.setProperty("awt.useSystemAAFontSettings", "on");
        adjustPanel(this);

        myMainMenuButton = buildButton("Main Menu");
        myHelpButton = buildButton("Help");
        myKeyBindingsButton = buildButton("Key Bindings");
        myAboutButton = buildButton("About");
        myHelpButton.addActionListener(e -> showResource(this,"/help.txt",
                "Help"));
        myKeyBindingsButton.addActionListener(e -> showResource(this,
                "/key_bindings.txt", "Key Bindings"));
        myAboutButton.addActionListener(e -> showResource(this, "/about.txt",
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

    /**
     * Makes a difficulty button and adds it to a unified panel.
     *
     * @param theDim    the dimension of the maze created by the button.
     * @param theColor  the color of the button.
     * @param theGame   the main GUI for gameplay.
     */
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
