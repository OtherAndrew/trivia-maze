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

    /**
     * Finds a path from the player's location to the goal location. If a path
     * exists then the List of Rooms that lead to the goal will be returned.
     * If a path does not exist then an empty List will be returned.
     *
     * @param theMaze the Maze to find a path for.
     * @return the List of Rooms that leads to the goal, or an empty List if
     *         no such path exists.
     */
    public static List<Room> findPath(final Maze theMaze) {
        final Map<Node, Room> visited = new HashMap<>();
        final LinkedList<Node> toVisit = new LinkedList<>();
        toVisit.add(new Node(theMaze.getPlayerLocation()));

        while (!toVisit.isEmpty()) {
            final Node cur = toVisit.remove();
            final Room curRoom = cur.myRoom;

            if (visited.containsKey(cur)) continue;

            if (curRoom.getDoorCount() == 1
                    && curRoom != theMaze.getPlayerLocation()
                    && curRoom != theMaze.getGoalLocation()) {
                visited.put(cur, curRoom);
                continue;
            }

            if (curRoom == theMaze.getGoalLocation()) return tracePath(cur);

            for (Door door : curRoom.getAllDoors()) {
                if (door.getState() != State.LOCKED) {
                    final Room otherRoom = door.getOtherSide(curRoom);
                    if (!visited.containsValue(otherRoom))
                        toVisit.add(new Node(otherRoom, cur));
                }
            }
            visited.put(cur, curRoom);
        }
        return Collections.emptyList();
    }

    /**
     * Traces the path from the goal Node to the player's position.
     *
     * @param theGoal the Node that contains the goal room.
     * @return a List of Rooms from the player's position to the goal
     */
    private static List<Room> tracePath(final Node theGoal) {
        final LinkedList<Room> path = new LinkedList<>();
        Node iter = theGoal;
        while (iter != null) {
            path.push(iter.myRoom);
            iter = iter.myParent;
        }
        return path;
    }
}


