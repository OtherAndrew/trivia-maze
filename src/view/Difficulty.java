package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static view.AppTheme.*;

public class Difficulty {

    private final JPanel myPanel;
    private final JPanel myMenubar;
    private final JPanel myDifficultyPanel;
    private final JButton myMainMenuBtn;
    private final JButton myEasyButton;
    private final JButton myMediumButton;
    private final JButton myHardButton;
    private final JButton myInsaneButton;

    public Difficulty(final JPanel thePanel, final CardLayout theCards) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        myPanel = buildPanel();

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

        myMenubar = buildMenubar(new JComponent[]{buildBufferPanel(),
                buildBufferPanel(), myMainMenuBtn});

        myPanel.add(myMenubar, BorderLayout.NORTH);
        myPanel.add(myDifficultyPanel, BorderLayout.CENTER);

        myMainMenuBtn.addActionListener(theAction -> theCards.show(thePanel,
                "start"));

        // TODO: assign difficulty settings
        final JButton[] difficultyButtons = {myEasyButton, myMediumButton,
                myHardButton, myInsaneButton};
        for (JButton button : difficultyButtons) {
            button.addActionListener(theAction -> {
                theCards.show(thePanel, "game"); // PLACEHOLDER
            });
            myDifficultyPanel.add(button);
        }

        // TODO: dev mode button/buttons (omniscient map, always open doors,
        //  highlighted answers, etc)

        myPanel.setVisible(true);
    }

    public JPanel getPanel() {
        return myPanel;
    }
}
