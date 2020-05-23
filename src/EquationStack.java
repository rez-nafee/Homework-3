//Rezvan Nafee
//112936468
//Recitation 04

import java.util.ArrayList;

/**
 * This class represents a Stack implantation using ArrayLists that will be used to help evaluate the postfix,
 * prefix, and answer of an equation.
 *
 * @author Rezvan Nafee
 * @ID 112936468
 * @Recitation Section 04
 */
public class EquationStack extends ArrayList {
    private ArrayList<String> equationStack = new ArrayList<>();

    /**
     * This is a constructor that creates an ArrayList that holds Strings called equationStack to hold parts of
     * expressions to be manipulated.
     */
    public EquationStack() {
    }

    /**
     * Adds a string to the first position of the ArrayList, and if there is any element in the fist spot of the
     * ArrayList it will be shifted down.
     *
     * @param s The String that would like to be added to the stack.
     */
    public void push(String s) {
        equationStack.add(0, s);
    }

    /**
     * Returns the first item in the list, or in other words the last item that was entered into the stack, and removes
     * it from the ArrayList. If there are no items currently in the ArrayList, it returns a null value.
     *
     * @return The first item the in the ArrayList.
     */
    public String pop() {
        if (equationStack.size() == 0)
            return null;
        String firstElement = equationStack.get(0);
        equationStack.remove(0);
        return firstElement;
    }

    /**
     * Returns the first item in the list, or in other words the last item that was entered into the stack. If there
     * are no items currently in the ArrayList, it returns a null value.
     *
     * @return The first item the in the ArrayList.
     */
    public String peek() {
        if (equationStack.isEmpty())
            return null;
        else return equationStack.get(0);
    }

    /**
     * If there is more than one element in the ArrayList, the it is is not empty. However, if it is empty, there are
     * no elements in the ArrayList.
     *
     * @return Returns true if there is more than one element in the ArrayList.
     * Returns false if there are is no items in the ArrayList.
     */
    public boolean isEmpty() {
        return equationStack.size() == 0;
    }

    /**
     * Returns the ArrayList that is holding the String elements
     *
     * @return The equationStack.
     */
    public ArrayList<String> getEquationStack() {
        return equationStack;
    }

    /**
     * Sets the ArrayList to a specified ArrayList
     *
     * @param equationStack the Specified ArrayList
     */
    public void setEquationStack(ArrayList<String> equationStack) {
        this.equationStack = equationStack;
    }

}
