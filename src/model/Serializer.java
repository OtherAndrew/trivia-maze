package model;

import model.Maze.Memento;

import java.io.*;
import java.util.Optional;

public class Serializer {

    /**
     * Empty constructor
     */
    private Serializer() {}

    public static void save(final Memento theMaze, final File theSaveFile) {
        try {
            final FileOutputStream file = new FileOutputStream(theSaveFile);
            final ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(theMaze);
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
