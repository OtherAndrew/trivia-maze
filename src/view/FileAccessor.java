package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class FileAccessor {

    private static FileAccessor uniqueInstance;

    final JFileChooser myChooser;
    File myLoadFile;
    File mySaveFile;

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

    public void loadFile() {
        myChooser.setDialogTitle("Load a previous game");
        if (myChooser.showDialog(null, "Resume") == JFileChooser.APPROVE_OPTION) {
            myLoadFile = myChooser.getSelectedFile();
        }
    }

    public void saveFile() {
        myChooser.setDialogTitle("Save your game");
        if (myChooser.showDialog(null, "Save") == JFileChooser.APPROVE_OPTION) {
            mySaveFile = myChooser.getSelectedFile();
        }
    }

    public File getLoadFile() {
        return myLoadFile;
    }

    public File getSaveFile() {
        return mySaveFile;
    }
}
