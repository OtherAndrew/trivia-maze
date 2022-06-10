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

/**
 * FileAccessor is used for dialog-related tasks. It can bring up a file
 * chooser for saving and loading or offer notification-style dialog.
 */
public final class FileAccessor {

    /**
     * The unique instance of the FileAccessor.
     */
    private static FileAccessor myAccessor;

    /**
     * Inner tool to retrieve files.
     */
    private final JFileChooser myChooser;

    /**
     * Creates a new FileAccessor.
     */
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

    /**
     * Retrieves the unique FileAccessor or creates it as necessary.
     *
     * @return the unique instance.
     */
    static FileAccessor getFileAccessor() {
        return Optional.ofNullable(myAccessor).orElse(myAccessor = new FileAccessor());
    }

    /**
     * Gets a file for loading.
     *
     * @param theParent the parent component for the dialog.
     * @return the file or null.
     */
    Optional<File> loadFile(final Component theParent) {
        return getFile(theParent, "Load a previous game", "Resume");
    }

    /**
     * Gets a file for saving.
     *
     * @param theParent the parent component for the dialog.
     * @return the file or null.
     */
    Optional<File> saveFile(final Component theParent) {
        return getFile(theParent, "Save your game", "Save");
    }

    /**
     * Customizes the dialog for and retrieves a file.
     *
     * @param theParent the parent component for the dialog.
     * @param theTitle  the title of the dialog.
     * @param theButton the text of the button.
     * @return the file or null.
     */
    private Optional<File> getFile(final Component theParent, final String theTitle,
                                   final String theButton) {
        myChooser.setDialogTitle(theTitle);
        File file = null;
        if (myChooser.showDialog(theParent, theButton) == JFileChooser.APPROVE_OPTION)
            file = myChooser.getSelectedFile();
        return Optional.ofNullable(file);
    }

    /**
     * Retrieves and displays the contents of a file.
     *
     * @param theParent     the parent component for the dialog.
     * @param theFilePath   the file path for the file to display.
     * @param theTitle      the title for the dialog.
     */
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
