package model;

import model.mazecomponents.Room;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Disjoint set for Room objects using a hashmap.
 *
 * @author Anthony Nguyen
 * @author Lagi (original)
 */
public class HashMapDisjointSet {

    private final Map<Room, Room> myMap;
    private final Map<Room, Integer> mySizes;
    private int mySize;

    /**
     * Creates a new disjoint set, in which each item is in its own group.
     *
     * @param theRooms  the items comprising groups in the disjoint set.
     */
    public HashMapDisjointSet(final Room[][] theRooms) {
        myMap = new HashMap<>();
        mySizes = new HashMap<>();
        for (Room[] outer : theRooms) {
            for (Room room : outer) {
                myMap.put(room, room);
                mySizes.put(room, 1);
            }
        }
        mySize = theRooms.length * theRooms[0].length;
    }

    /**
     * Finds the group in which the given item is found.
     *
     * Data structure optimization:
     * while searching, makes all nodes in the search path to point directly at
     * the root of the group, which will be returned.
     *
     * @param theRoom   the item for which group to search.
     * @return the item at the root of the found group.
     */
    public Room find(final Room theRoom) {
        Room root = theRoom;
        while (!myMap.get(root).equals(root)) {
            root = myMap.get(root);
        }
        while (!myMap.get(theRoom).equals(root)) {
            myMap.replace(theRoom, root);
        }
        return root;
    }

    /**
     * Joins two groups together.
     *
     * Data structure optimization:
     * joins the smaller group to the larger to avoid long sequences.
     *
     * @param theRoom1  item belonging to first group.
     * @param theRoom2  item belonging to second group.
     */
    public void join(final Room theRoom1, final Room theRoom2) {
        Room group1 = find(theRoom1);
        Room group2 = find(theRoom2);
        --mySize;
        if (mySizes.get(group1) > mySizes.get(group2)) {
            joinHelper(group1, group2);
        } else {
            joinHelper(group2, group1);
        }
    }

    /**
     * Aids in process of joining two groups together.
     *
     * @param theLarger     the larger group.
     * @param theSmaller    the smaller group.
     */
    private void joinHelper(final Room theLarger,
                            final Room theSmaller) {
        myMap.replace(theSmaller, theLarger);
        Integer newSize = mySizes.get(theLarger) + mySizes.get(theSmaller);
        mySizes.replace(theLarger, newSize);
    }

    /** @return size of the disjoint set. */
    public int getSize() {
        return mySize;
    }
}
