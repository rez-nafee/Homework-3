//Rezvan Nafee
//112936468
//Recitation 04

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class represents the Calculator history in terms of what expressions the user has entered to be evaluated. In
 * this class, you are able to modify the entries in which the user has entered them. For example you are able to add,
 * replace, and remove certain characters from a the String of the Equation to produce a new expression to be evaluated.
 *
 * @author Rezvan Nafee
 * @ID 112936468
 * @Recitation Section 04
 */
public class HistoryStack extends ArrayList {
    /**
     * Decimal format will help display the answer rounded to the nearest 3 decimal places.
     */
    public DecimalFormat df = new DecimalFormat("0.000");
    private ArrayList<Equation> history = new ArrayList<Equation>();
    private UndoStack undos = new UndoStack();
    private Equation lastEquation;
    private int positionOfLastEquation;

    /**
     * This is a constructor the creates an ArrayList of equations to keep track of what Equations were entered into
     * the calculator.
     */
    public HistoryStack() {
    }

    /**
     * Adds the equation to the first index of the ArrayList and shifts everything else down one index if there is
     * currently in the first index.
     *
     * @param newEquation The Equation that would like to be added.
     */
    public void push(Equation newEquation) {
        history.add(0, newEquation);
        lastEquation = newEquation;
        positionOfLastEquation = history.size();
    }

    /**
     * Removes and returns the first element of the ArrayList. If there are not items currently in the ArrayList, it
     * returns a null value.
     *
     * @return Returns the first element of the ArrayList or it returns a null value if there is nothing to remove.
     */
    public Equation pop() {
        if (history.size() == 0) {
            return null;
        } else {
            Equation temp = history.get(0);
            history.remove(0);
            return temp;
        }
    }

    /**
     * Returns the first element of the ArrayList. If there are no items in the ArrayList, it returns a null value.
     *
     * @return The first element of the ArrayList
     */
    public Equation peek() {
        if (history.size() == 0) {
            return null;
        } else {
            return history.get(0);
        }
    }

    /**
     * Retuns the number of elements in the ArrayList.
     *
     * @return
     */
    public int size() {
        return history.size();
    }

    /**
     * Utilizes the pop method and pushes it into the undo stack to hold for later use.
     *
     * @throws Exception
     */
    public void undo() throws Exception {
        if (history.size() == 0)
            throw new Exception("There is nothing to undo!");
        else {
            Equation temp = pop();
            undos.push(temp);
        }
    }

    /**
     * Utilizes the push of HistoryStack and pop of UndoStack to redo the removed elements from using undo() back into
     * the HistoryStack.
     *
     * @throws Exception
     */
    public void redo() throws Exception {
        if (undos.size() == 0) {
            throw new Exception("There is nothing to redo!");
        } else {
            Equation holdLast = this.lastEquation;
            int holdPosition = this.positionOfLastEquation;
            Equation temp = undos.pop();
            push(temp);
            setLastEquation(holdLast);
            setPositionOfLastEquation(holdPosition);
        }
    }

    /**
     * Removes all the elements that are currently in the calculator
     */
    public void clearHistory() {
        while (history.size() != 0) {
            pop();
        }
        while (undos.size() != 0) {
            undos.pop();
        }
    }

    /**
     * Returns the String representation of HistoryStack which highlights the last inputted Equation and along with its
     * Equation properties.
     *
     * @return The String representation of the HistoryStack
     */
    public String toString() {
        String result = "";
        String header = String.format("%-10s%-40s%-50s%-50s%-30s%-30s%-30s", "#", "Equation", "Pre-Fix", "Post-Fix",
                "Answer"
                , "Binary", "Hexadecimal");
        String divider = ("\n--------------------------------------------------------------------------------------" +
                "---------------------------------------------------------------------------------------------------" +
                "-------------------------------------\n");
        result += header + divider;
        HistoryStack tempStack = new HistoryStack();
        while (!history.isEmpty()) {
            Equation temp = peek();
            result += String.format("%-10d%-40s%-50s%-50s%-30s%-30s%-30s",
                    history.size(), temp.getEquation(), temp.getPrefix(), temp.getPostfix(),
                    df.format(temp.getAnswer()), temp.getBinary(), temp.getHex());
            result += "\n";
            tempStack.push(pop());
        }
        while (!tempStack.isEmpty()) {
            push(tempStack.pop());
        }
        return result;
    }

    /**
     * Returns the Equation at a specified position in the ArrayList. If the position is not a valid index or if there
     * is no Equation at the provided index, then it returns null.
     *
     * @param pos The position of the ArrayList
     * @return Returns the Equation at the specified location.
     * Returns null if the Equation does not exist at the location or if the position is an invalid index.
     */
    public Equation findEquation(int pos) {
        if (pos - 1 < 0) {
            return null;
        }
        if (pos > history.size())
            return null;
        return history.get(pos - 1);
    }

    /**
     * Returns the String representation of the HistoryStack, however it only prints the last entered Equation.
     *
     * @return The String representation of the last entered Equation of the History Stack.
     */
    public String lastEquation() {
        String result = "";
        String header = String.format("%-10s%-40s%-50s%-50s%-30s%-30s%-30s", "#", "Equation", "Prefix", "Postfix",
                "Answer",
                "Binary", "Hexadecimal");
        String divider = ("\n--------------------------------------------------------------------------------------" +
                "---------------------------------------------------------------------------------------------------" +
                "-------------------------------------\n");
        result += header + divider;
        if (lastEquation == null)
            return result;
        else {
            result += String.format("%-10d%-40s%-50s%-50s%-30s%-30s%-30s",
                    positionOfLastEquation, lastEquation.getEquation(), lastEquation.getPrefix(),
                    lastEquation.getPostfix(),
                    df.format(lastEquation.getAnswer()), lastEquation.getBinary(), lastEquation.getHex());
            result += "\n";
        }
        return result;
    }

    /**
     * Modifies the String property of an Equation by adding a specific item to a specific position. After that it
     * it returns the modified Equation.
     *
     * @param pos   The position of the Equation to be modified.
     * @param index The index of the String property of the Equation to be modified.
     * @param add   The thing to add to the Equation.
     * @return The modified Equation
     * @throws Exception
     */
    public Equation addSomething(int pos, int index, String add) throws Exception {
        if (pos < 0)
            return null;
        if (pos > history.size())
            return null;
        else {
            Equation temp = findEquation(pos);
            String newEquation = "";
            if (index - 1 >= 0 && index - 1 < temp.getEquation().length()) {
                for (int i = 0; i < temp.getEquation().length(); i++) {
                    char key = temp.getEquation().charAt(i);
                    if (i == index - 1) {
                        newEquation += add;
                        newEquation += key;
                    } else {
                        newEquation += key;
                    }
                }

                Equation moddedEquation = new Equation(newEquation);
                return moddedEquation;
            } else {
                return null;
            }
        }
    }

    /**
     * Modified a specified equation to remove a specified character at a specified index in the String property of
     * Equation.
     *
     * @param pos   The position of the Equation to the modified.
     * @param index The index of the String property of the Equation.
     * @return
     * @throws Exception
     */
    public Equation removeSomething(int pos, int index) throws Exception {
        if (pos < 0)
            return null;
        if (pos > history.size())
            return null;
        else {
            Equation temp = findEquation(pos);
            String newEquation = "";
            if (index - 1 >= 0 && index - 1 < temp.getEquation().length()) {
                for (int i = 0; i < temp.getEquation().length(); i++) {
                    char key = temp.getEquation().charAt(i);
                    if (i == index - 1) {
                        continue;
                    } else {
                        newEquation += key;
                    }
                }

                Equation moddedEquation = new Equation(newEquation);
                return moddedEquation;
            } else {
                return null;
            }
        }
    }

    /**
     * Modifies the String property of an Equation by replacing a specific item to a specific character . After that it
     * it returns the modified Equation.
     *
     * @param pos     The position of the Equation to be modified.
     * @param index   The index of the String of the Equation to be modified.
     * @param replace The thing to replace the character in  to the Equation.
     * @return The modified Equation
     * @throws Exception
     */
    public Equation replaceSomething(int pos, int index, String replace) throws Exception {
        if (pos < 0)
            return null;
        if (pos > history.size())
            return null;
        else {
            Equation temp = findEquation(pos);
            String newEquation = "";
            if (index - 1 >= 0 && index - 1 < temp.getEquation().length()) {
                for (int i = 0; i < temp.getEquation().length(); i++) {
                    char key = temp.getEquation().charAt(i);
                    if (i == index - 1) {
                        newEquation += replace;
                    } else {
                        newEquation += key;
                    }
                }

                Equation moddedEquation = new Equation(newEquation);
                return moddedEquation;
            } else {
                return null;
            }
        }
    }

    /**
     * If there is more than one element in the ArrayList, the it is is not empty. However, if it is empty, there are
     * no elements in the ArrayList.
     *
     * @return Returns true if there is more than one element in the ArrayList.
     * Returns false if there are is no items in the ArrayList.
     */
    public boolean isEmpty() {
        return history.size() == 0;
    }


    /**
     * Sets the ArrayList to a specified ArrayList
     *
     * @param history The specified ArrayList
     */
    public void setHistory(ArrayList<Equation> history) {
        this.history = history;
    }

    /**
     * Sets the UndoStack to a specified UndoStack
     *
     * @param undos The specified UndoStack
     */
    public void setUndos(UndoStack undos) {
        this.undos = undos;
    }

    /**
     * Returns the UndoStack
     *
     * @return The UndoStack
     */
    public UndoStack getUndos() {
        return undos;
    }

    /**
     * Returns the last entered equation of the History Stack
     *
     * @return
     */
    public Equation getLastEquation() {
        return lastEquation;
    }

    /**
     * SEts the last entered equation to a specified equation
     *
     * @param lastEquation The specified Equation.
     */
    public void setLastEquation(Equation lastEquation) {
        this.lastEquation = lastEquation;
    }

    /**
     * Returns the  history ArrayList.
     *
     * @return The history ArrayList
     */
    public ArrayList<Equation> getHistory() {
        return history;
    }

    /**
     * Returns the position of where the last entered equation.
     *
     * @return The position of the last entered equation.
     */
    public int getPositionOfLastEquation() {
        return positionOfLastEquation;
    }

    /**
     * Sets the position of the last entered equation to a specified position.
     *
     * @param positionOfLastEquation The specified position number.
     */
    public void setPositionOfLastEquation(int positionOfLastEquation) {
        this.positionOfLastEquation = positionOfLastEquation;
    }
}

