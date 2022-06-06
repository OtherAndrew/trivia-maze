package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Optional;

public class FileAccessor {

    private static FileAccessor uniqueInstance;

    private final JFileChooser myChooser;
    
    private FileAccessor() {
        final File saveFolder = new File(
                FileSystemView.getFileSystemView().getDefaultDirectory() + "/trivia-maze");
        saveFolder.mkdir();
        myChooser = new JFileChooser(saveFolder);
        myChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        myChooser.setAcceptAllFileFilterUsed(false);
        myChooser.addChoosableFileFilter(new FileNameExtensionFilter(
                "Trivia Maze .ser file", "ser"));
    }

    static FileAccessor getInstance() {
        return Optional.ofNullable(uniqueInstance).orElse(uniqueInstance = new FileAccessor());
    }

    Optional<File> loadFile() {
        myChooser.setDialogTitle("Load a previous game");
        File myLoadFile = null;
        if (myChooser.showDialog(null, "Resume") == JFileChooser.APPROVE_OPTION) {
            myLoadFile = myChooser.getSelectedFile();
        }
        return Optional.ofNullable(myLoadFile);
    }

    Optional<File> saveFile() {
        myChooser.setDialogTitle("Save your game");
        File mySaveFile = null;
        if (myChooser.showDialog(null, "Save") == JFileChooser.APPROVE_OPTION) {
            mySaveFile = myChooser.getSelectedFile();
        }
        return Optional.ofNullable(mySaveFile);
    }
}
