package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Optional;

public class FileAccessor {

    private static FileAccessor uniqueInstance;

    final JFileChooser myChooser;
    
    private FileAccessor() {
        myChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        myChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        myChooser.setAcceptAllFileFilterUsed(false);
        final FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Trivia Maze .ser file", "ser");
        myChooser.addChoosableFileFilter(filter);
    }

    public static FileAccessor getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new FileAccessor();
        }
        return uniqueInstance;
    }

    public Optional<File> loadFile() {
        myChooser.setDialogTitle("Load a previous game");
        File myLoadFile = null;
        if (myChooser.showDialog(null, "Resume") == JFileChooser.APPROVE_OPTION) {
            myLoadFile = myChooser.getSelectedFile();
        }
        return Optional.ofNullable(myLoadFile);
    }

    public Optional<File> saveFile() {
        myChooser.setDialogTitle("Save your game");
        File mySaveFile = null;
        if (myChooser.showDialog(null, "Save") == JFileChooser.APPROVE_OPTION) {
            mySaveFile = myChooser.getSelectedFile();
        }
        return Optional.ofNullable(mySaveFile);
    }
}
