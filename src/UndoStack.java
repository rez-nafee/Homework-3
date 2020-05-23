//Rezvan Nafee
//112936468
//Recitation 04

import java.util.ArrayList;

/**
 * This class represents a Stack implantation that uses ArrayList that holds Equation to keep track of what was being
 * undone from the HistoryStack. This class also helps push those undone items back into the HistoryStack to be marked
 * as redone.
 *
 * @author Rezvan Nafee
 * @ID 112936468
 * @Recitation Section 04
 */
public class UndoStack extends ArrayList {
    private ArrayList<Equation> undo = new ArrayList<>();

    /**
     * This is a constructor that creates an ArrayList that holds Equations called undo to hold equations that were
     * removed from the the History Stack. This also allows us to move those items back into the HistoryStack.
     */
    public UndoStack() {
    }

    /**
     * Adds the equation to the first index of the ArrayList and shifts everything else down one index if there is
     * currently in the first index.
     *
     * @param newEquation The Equation that would like to be added.
     */
    public void push(Equation newEquation) {
        undo.add(0, newEquation);
    }

    /**
     * Removes and returns the first element of the ArrayList. If there are not items currently in the ArrayList, it
     * returns a null value.
     *
     * @return Returns the first element of the ArrayList or it returns a null value if there is nothing to remove.
     */
    public Equation pop() {
        if (undo.size() == 0) {
            return null;
        } else {
            Equation temp = undo.get(0);
            undo.remove(0);
            return temp;
        }
    }

    /**
     * Returns the first element of the ArrayList. If there are no items in the ArrayList, it returns a null value.
     *
     * @return the first element of the ArrayList
     */
    public Equation peek() {
        if (undo.size() == 0) {
            return null;
        } else {
            return undo.get(0);
        }
    }

    /**
     * Retuns the number of elements in the ArrayList.
     *
     * @return
     */
    public int size() {
        return undo.size();
    }

    /**
     * If there is more than one element in the ArrayList, the it is is not empty. However, if it is empty, there are
     * no elements in the ArrayList.
     *
     * @return Returns true if there is more than one element in the ArrayList.
     * Returns false if there are is no items in the ArrayList.
     */
    public boolean isEmpty() {
        return undo.size() == 0;
    }

    /**
     * Returns the ArrayList that is holding the Equations
     *
     * @return The equationStack.
     */
    public ArrayList<Equation> getUndo() {
        return undo;
    }

    public void setUndo(ArrayList<Equation> undo) {
        this.undo = undo;
    }
}
