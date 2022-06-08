package model;

import model.mazecomponents.Door;
import model.mazecomponents.Room;
import model.mazecomponents.State;

import java.util.*;

/**
 * Finds the shortest path to the exit of a maze, provided it is possible.
 *
 * @author Anthony Nguyen
 * @author Amit Pandey (original)
 */
public class BFSRunner {

    /**
     * Empty constructor
     */
    private BFSRunner() {}

    /**
     * Finds a path from the player's location to the goal location. If a path
     * exists then the List of Rooms that lead to the goal will be returned.
     * If a path does not exist then an empty List will be returned.
     *
     * @param theMaze the Maze to find a path for.
     * @return the List of Rooms that leads to the goal, or an empty List if
     *         no such path exists.
     */
    public static Optional<Path> findPath(final Maze theMaze) {
        Path path = null;

        final Map<Node, Room> visited = new HashMap<>();
        final LinkedList<Node> toVisit = new LinkedList<>();
        toVisit.add(new Node(theMaze.getPlayerLocation()));

        while (!toVisit.isEmpty()) {
            final Node cur = toVisit.remove();
            final Room curRoom = cur.myRoom;

            if (curRoom == theMaze.getGoalLocation()) {
                path = tracePath(cur);
                break;
            }

            if (visited.containsKey(cur)) continue;

            if (curRoom.getDoorCount() == 1 && curRoom != theMaze.getPlayerLocation()) {
                visited.put(cur, curRoom);
                continue;
            }

            for (Door door : curRoom.getAllDoors()) {
                if (door.getState() != State.LOCKED) {
                    final Room otherRoom = door.getOtherSide(curRoom);
                    if (!visited.containsValue(otherRoom))
                        toVisit.add(new Node(otherRoom, cur));
                }
            }
            visited.put(cur, curRoom);
        }
        return Optional.ofNullable(path);
    }

    /**
     * Traces the path from the goal Node to the player's position.
     *
     * @param theGoal the Node that contains the goal room.
     * @return a List of Rooms from the player's position to the goal
     */
    private static Path tracePath(final Node theGoal) {
        final LinkedList<Room> rooms = new LinkedList<>();
        Node iter = theGoal;
        while (iter != null) {
            rooms.push(iter.myRoom);
            iter = iter.myParent;
        }
        final LinkedList<Door> doors = new LinkedList<>();
        for (int i = 0; i < rooms.size() - 1; i++) {
            final Room curRoom = rooms.get(i);
            for (Door door : curRoom.getAllDoors()) {
                if (door.getOtherSide(curRoom).equals(rooms.get(i + 1))) {
                    doors.add(door);
                }
            }
        }
        return new Path(rooms, doors);
    }

    /**
     * Node contains a reference to a Room and a parent Node.
     */
    private static class Node {
        /**
         * A room.
         */
        private final Room myRoom;
        /**
         * The parent node.
         */
        private final Node myParent;

        /**
         * Constructs a Node with no reference to another Node.
         *
         * @param theRoom the Room the Node contains.
         */
        private Node(final Room theRoom) {
            this(theRoom, null);
        }

        /**
         * Constructs a Node with a reference to another Node.
         *
         * @param theRoom the Room the Node contains.
         * @param theParent another Node.
         */
        private Node(final Room theRoom, final Node theParent) {
            myRoom = theRoom;
            myParent = theParent;
        }
    }
}


