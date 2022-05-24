package view;

import model.mazecomponents.Serializer;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class LoadGame {

    private JFileChooser myFileChooser;
    private final String myApproveBtnText = "Resume";

    public LoadGame() {
        myFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        myFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
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
