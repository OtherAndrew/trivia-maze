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

public final class FileAccessor {

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
        return getFile(theParent, "Load a previous game", "Resume");
    }

    Optional<File> saveFile(final Component theParent) {
        return getFile(theParent, "Save your game", "Save");
    }

    private Optional<File> getFile(final Component theParent, final String theTitle,
                                   final String theButton) {
        myChooser.setDialogTitle(theTitle);
        File file = null;
        if (myChooser.showDialog(theParent, theButton) == JFileChooser.APPROVE_OPTION)
            file = myChooser.getSelectedFile();
        return Optional.ofNullable(file);
    }

    static void showResource(final Component theParent,
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
