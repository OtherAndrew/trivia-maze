package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static view.AppTheme.*;

public class Difficulty {

    private JFrame myFrame;
    private JPanel myMenubar, myDifficultyPanel;
    private JButton myMainMenuBtn, myEasyButton, myMediumButton, myHardButton, myInsaneButton;
    private ImageIcon myWindowIcon;
    private final String myWindowIconPath = "resources\\App_Icon.png";

    public Difficulty() {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        myFrame = buildFrame();

        myMainMenuBtn = buildButton("Main Menu");

        myEasyButton = buildButton("4 x 4");
        myEasyButton.setBackground(GREEN);
        myEasyButton.setFont(LARGE_FONT);
        myMediumButton = buildButton("6 x 6");
        myMediumButton.setBackground(ORANGE);
        myMediumButton.setFont(LARGE_FONT);
        myHardButton = buildButton(" 8 x 8");
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


        myMenubar = buildMenubar(new JComponent[]{
                buildBufferPanel(), buildBufferPanel(), myMainMenuBtn});

        myFrame.add(myMenubar, BorderLayout.NORTH);
        myFrame.add(myDifficultyPanel, BorderLayout.CENTER);

        myMainMenuBtn.addActionListener(theAction -> {
            new Start();
            myFrame.dispose();
        });

        // TODO: assign difficulty settings
        final JButton[] difficultyButtons = {myEasyButton, myMediumButton, myHardButton, myInsaneButton};
        for (JButton button : difficultyButtons) {
            button.addActionListener(theAction -> {
                new Game();
                myFrame.dispose();
            });
            myDifficultyPanel.add(button);
        }

        // TODO: dev mode button/buttons (omniscient map, always open doors, highlighted answers, etc)

        myFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Difficulty d = new Difficulty();
    }
}
