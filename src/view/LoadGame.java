package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class LoadGame {

    final JFileChooser myChooser;
    File myFile;

    public LoadGame() {
        myChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        myChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        myChooser.setAcceptAllFileFilterUsed(false);
        final FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Trivia Maze .ser file", "ser");
        myChooser.addChoosableFileFilter(filter);
        myChooser.setDialogTitle("Load a previous game");
    }

    public void setFile() {
        if (myChooser.showDialog(null, "Resume") == JFileChooser.APPROVE_OPTION) {
            myFile = myChooser.getSelectedFile();
        }
    }

    public File getFile() {
        return myFile;
    }
}
