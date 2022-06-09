package model;

import model.Maze.Memento;

import java.io.*;
import java.util.Optional;

/**
 * Serializer saves and loads mementos.
 */
public final class Serializer {

    /**
     * Prevent instantiation.
     */
    private Serializer() {}

    /**
     * Checks the save file has the proper extension and adds it if not.
     *
     * @param theSaveFile   the file to check.
     * @return the file.
     */
    private static File checkExtension(final File theSaveFile) {
        File returnFile = theSaveFile;
        final String absolutePath = theSaveFile.getAbsolutePath();
        if (!absolutePath.endsWith(".ser")) {
            returnFile = new File(absolutePath + ".ser");
        }
        return returnFile;
    }

    /**
     * Saves the given memento to the specified file.
     * @param theMaze       the memento to be saved.
     * @param theSaveFile   the file to be written.
     */
    public static void save(final Memento theMaze, final File theSaveFile) {
        try {
            final FileOutputStream file = new FileOutputStream(checkExtension(theSaveFile));
            final ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(theMaze);
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a memento from a specified file.
     *
     * @param theSaveFile   the file to load from.
     * @return an Optional containing the memento if loading was successful,
     * else null.
     */
    public static Optional<Memento> load(final File theSaveFile) {
        Memento returnMaze;
        try {
            final FileInputStream file = new FileInputStream(theSaveFile);
            final ObjectInputStream in = new ObjectInputStream(file);
            returnMaze = (Memento) in.readObject();
            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException e) {
            returnMaze = null;
        }
        return Optional.ofNullable(returnMaze);
    }
}
