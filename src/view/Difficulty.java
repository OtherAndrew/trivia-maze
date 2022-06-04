package view;

import controller.TriviaMaze;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static view.AppTheme.*;
import static view.MazeDisplayBuilder.buildMapDisplay;

public class Difficulty extends JPanel {

    private final JPanel myMenubar;
    private final JPanel myDifficultyPanel;
    private final JButton myMainMenuBtn;
    private final JButton myEasyButton;
    private final JButton myMediumButton;
    private final JButton myHardButton;
    private final JButton myInsaneButton;

    public Difficulty(final Game theGame, final CardLayout theCards) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        adjustPanel(this);

        myMainMenuBtn = buildButton("Main Menu");

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

        final GridLayout difficultyLayout = new GridLayout(4, 1);
        difficultyLayout.setVgap(7);
        myDifficultyPanel = new JPanel(difficultyLayout);
        myDifficultyPanel.setBackground(MID_GREY);
        myDifficultyPanel.setBorder(new EmptyBorder(100, 100, 100, 100));

        myMenubar = buildMenubar(buildBufferPanel(), buildBufferPanel(), myMainMenuBtn);

        add(myMenubar, BorderLayout.NORTH);
        add(myDifficultyPanel, BorderLayout.CENTER);

        myMainMenuBtn.addActionListener(theAction -> theCards.show(theGame.getContentPanel(), "start"));

        // TODO: assign difficulty settings
        final JButton[] difficultyButtons = {myEasyButton, myMediumButton,
                myHardButton, myInsaneButton};
        int dim = 4;
        for (JButton button : difficultyButtons) {
            int finalDim = dim;
            button.addActionListener(theAction -> {
                theGame.getController().buildMaze(finalDim, finalDim);
                theGame.updateMapDisplay();
                theCards.show(theGame.getContentPanel(), "game"); // PLACEHOLDER
            });
            myDifficultyPanel.add(button);
            dim += 2;
        }

        // TODO: dev mode button/buttons (omniscient map, always open doors,
        //  highlighted answers, etc)
    }
}
