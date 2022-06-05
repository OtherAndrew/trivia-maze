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
    private final JButton myMainMenuBtn;
    private final JButton myEasyButton;
    private final JButton myMediumButton;
    private final JButton myHardButton;
    private final JButton myInsaneButton;
    private final JButton myHelpButton;
    private final JRadioButton myMasterKeyRadio;
    private final JRadioButton myXRayRadio;

    private boolean myMasterKey;
    private boolean myXRay;

    Difficulty(final Game theGame, final CardLayout theCards) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        adjustPanel(this);
        myMasterKey = false;
        myXRay = false;

        myMainMenuBtn = buildButton("Main Menu");
        myHelpButton = buildButton("Help");

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

        myMasterKeyRadio = buildRadioButton("Master Key");
        myXRayRadio = buildRadioButton("X-Ray");
        myHelpButton.addActionListener(e -> {
            try {
                final String helpText = Files.readString(Path.of(
                        "resources/helpMessage.txt"));
                JOptionPane.showMessageDialog(this,
                        helpText, "Help",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        myMenubar = buildMenubar(buildBufferPanel(), myHelpButton, myMainMenuBtn);

        final GridLayout difficultyLayout = new GridLayout(5, 1);
        difficultyLayout.setVgap(7);
        myDifficultyPanel = new JPanel(difficultyLayout);
        myDifficultyPanel.setBackground(MID_GREY);
        myDifficultyPanel.setBorder(new EmptyBorder(100, 100, 100, 100));

        myCheatPanel = new JPanel(new GridLayout(1, 2));
        myCheatPanel.add(myMasterKeyRadio);
        myCheatPanel.add(myXRayRadio);

        add(myMenubar, BorderLayout.NORTH);
        add(myDifficultyPanel, BorderLayout.CENTER);

        myMainMenuBtn.addActionListener(e -> theCards.show(theGame.getContentPanel(), "start"));

        myMasterKeyRadio.addActionListener(e -> myMasterKey = myMasterKeyRadio.isSelected());

        myXRayRadio.addActionListener(e -> myXRay = myXRayRadio.isSelected());

        int dim = 4;
        for (JButton button : new JButton[]{myEasyButton, myMediumButton, myHardButton, myInsaneButton}) {
            int finalDim = dim;
            button.addActionListener(e -> {
                theGame.getController()
                        .buildMaze(finalDim, finalDim, myMasterKey, myXRay);
                theGame.updateMapDisplay(false);
                theGame.updateQA();
                theCards.show(theGame.getContentPanel(), "game");
            });
            dim += 2;
            myDifficultyPanel.add(button);
        }
        myDifficultyPanel.add(myCheatPanel);

        // TODO: dev mode button/buttons (omniscient map, always open doors,
        //  highlighted answers, etc)
    }
}
