package model.mazecomponents;

import model.Maze;

import java.util.*;

/**
 * Finds the shortest path to the exit of a maze, provided it is possible.
 *
 * @author Anthony Nguyen
 * @author Amit Pandey (original)
 */
public class BFSRunner {

    private static class Node {
        private final Room myRoom;
        private final Node myParent;

        private Node(final Room theRoom) {
            this(theRoom, null);
        }

        private Node(final Room theRoom, final Node theParent) {
            myRoom = theRoom;
            myParent = theParent;
        }
    }

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

            for (Door door : curRoom.getAllDoors().values()) {
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

    private static LinkedList<Room> tracePath(final Node theExit) {
        final LinkedList<Room> path = new LinkedList<>();
        Node iter = theExit;
        while (iter != null) {
            path.push(iter.myRoom);
            iter = iter.myParent;
        }
        return path;
    }
}


