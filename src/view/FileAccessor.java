package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileAccessor {

    private static FileAccessor myAccessor;

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

    static FileAccessor getAccessor() {
        return Optional.ofNullable(myAccessor).orElse(myAccessor = new FileAccessor());
    }

    Optional<File> loadFile(final Component theParent) {
        myChooser.setDialogTitle("Load a previous game");
        File myLoadFile = null;
        if (myChooser.showDialog(theParent, "Resume") == JFileChooser.APPROVE_OPTION) {
            myLoadFile = myChooser.getSelectedFile();
        }
        return Optional.ofNullable(myLoadFile);
    }

    Optional<File> saveFile(final Component theParent) {
        myChooser.setDialogTitle("Save your game");
        File mySaveFile = null;
        if (myChooser.showDialog(theParent, "Save") == JFileChooser.APPROVE_OPTION) {
            mySaveFile = myChooser.getSelectedFile();
        }
        return Optional.ofNullable(mySaveFile);
    }

    static void showDialog(final Component theParent,
                                  final String theFilePath,
                                  final String theTitle) {
        try (final InputStream in = AppTheme.class.getResourceAsStream(theFilePath);
             final BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(in)))) {
            JOptionPane.showMessageDialog(theParent,
                    br.lines().collect(Collectors.joining("\n")), theTitle,
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
}
