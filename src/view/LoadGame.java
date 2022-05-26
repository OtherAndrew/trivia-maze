package view;

import model.Serializer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class LoadGame {

    private JFileChooser myFileChooser;
    private final String myApproveBtnText = "Resume";

    public LoadGame() {
        myFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        myFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        myFileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Trivia Maze .ser file", "ser");
        myFileChooser.addChoosableFileFilter(filter);
        myFileChooser.setDialogTitle("Load a previous game");
        int returnValue = myFileChooser.showDialog(null, myApproveBtnText);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = myFileChooser.getSelectedFile();
            Serializer.load(selectedFile);
        } else {
            new LoadOrStartNewGame();
        }
    }

    public static void main(String[] theArgs) {
        LoadGame loadGame = new LoadGame();
    }

}
