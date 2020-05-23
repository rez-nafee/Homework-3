//Rezvan Nafee
//112936468
//Recitation 04

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represents a simulation of using a calculator. The Calculator uses HistoryStack to keep track of the
 * equations being added and evaluated.
 *
 * @author Rezvan Nafee
 * @ID 112936468
 * @Recitation Section 04
 */
public class Calculator {
    /**
     * The main method where the user can simulate using a calculator by having inputted expressions be evaluated,
     * managed, collected, and removed. The user is also able to print the equations they have entered into the
     * calculators in the order they entered expressions in along with the last entered expression.
     *
     * @param args The command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to calculat0r!\n");
        Scanner input = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("0.000");
        Equation eq;
        HistoryStack historyStack = new HistoryStack();
        String key = "";
        while (!key.equals("Q")) {
            printTable();
            System.out.print("\nSelect an option: ");
            key = input.nextLine().trim().toUpperCase();
            switch (key) {
                case ("A"):
                    System.out.print("\nPLease enter an equation (in-fix notation): ");
                    String expression = input.nextLine().trim();
                    eq = new Equation(expression);
                    if (!eq.getBalanced())
                        System.out.println("The equation is not balanced!\n");
                    else
                        System.out.println("The equation is balanced and the answer is "
                                + df.format(eq.getAnswer()) + "!\n");
                    historyStack.push(eq);
                    break;
                case ("F"):
                    int positionOfEquation;
                    Equation changed = new Equation();
                    System.out.print("\nWhich equation would you like to change? ");
                    try {
                        positionOfEquation = input.nextInt();
                        input.nextLine();
                    } catch (InputMismatchException ex) {
                        input.nextLine();
                        System.out.println("\nChanging Equation Error: Information given is invalid!\n");
                        continue;
                    }
                    if (historyStack.findEquation(positionOfEquation) == null) {
                        System.out.println("Changing Equation Error: Information given is invalid!\n");
                    }
                    String userWord = "";
                    System.out.println("\nEquation at Position " + positionOfEquation + ": " +
                            historyStack.findEquation(positionOfEquation).getEquation());
                    System.out.print("Would you like to do to the equation (Replace/remove/add)? ");
                    userWord = input.nextLine().trim().toLowerCase();
                    if (!(userWord.equals("remove") || userWord.equals("add") || userWord.equals("replace"))) {
                        System.out.println("\nChanging Equation Error: Information given is invalid!");
                        continue;
                    } else if (userWord.equals("replace")) {
                        int changePosition;
                        System.out.print("What position would you like to change? ");
                        try {
                            changePosition = input.nextInt();
                            input.nextLine();
                        } catch (InputMismatchException ex) {
                            input.nextLine();
                            System.out.print("What would you like to replace it with? ");
                            input.nextLine();
                            System.out.println("\nChanging Equation Error: Information given is invalid!");
                            continue;
                        }
                        String changeWith;
                        System.out.print("What would you like to replace it with? ");
                        changeWith = input.nextLine().trim();
                        Equation temp = historyStack.replaceSomething(positionOfEquation, changePosition, changeWith);
                        changed = temp;
                        if (temp == null) {
                            System.out.println("\nChanging Equation Error: Can't modify the equation with given info!");
                            continue;
                        } else {
                            changed = temp;
                        }
                    } else if (userWord.equals("add")) {
                        int addPosition;
                        System.out.print("What position would you like to add something ? ");
                        try {
                            addPosition = input.nextInt();
                            input.nextLine();
                        } catch (InputMismatchException ex) {
                            input.nextLine();
                            System.out.print("What position would you like to add? ");
                            input.nextLine();
                            System.out.println("\nChanging Equation Error: Information given is invalid!");
                            continue;
                        }
                        String addAtPosition;
                        System.out.print("What would you like to add? ");
                        addAtPosition = input.nextLine().trim();
                        Equation temp = historyStack.addSomething(positionOfEquation, addPosition, addAtPosition);
                        if (temp == null) {
                            System.out.println("\nChanging Equation Error: Can't modify the equation with given info!");
                            continue;
                        } else {
                            changed = temp;
                        }
                    } else if (userWord.equals("remove")) {
                        int removePosition;
                        System.out.print("What position would you like to remove? ");
                        try {
                            removePosition = input.nextInt();
                            input.nextLine();
                        } catch (InputMismatchException ex) {
                            input.nextLine();
                            System.out.println("\nChanging Equation Error: Information given is invalid!");
                            continue;
                        }
                        Equation temp = historyStack.removeSomething(positionOfEquation, removePosition);
                        if (temp == null) {
                            System.out.println("\nChanging Equation Error: Can't modify the equation with given info!");
                            continue;
                        } else {
                            changed = temp;
                        }
                    }
                    boolean flag = true;
                    while (flag) {
                        historyStack.push(changed);
                        System.out.println("\nEquation:  " + changed.getEquation());
                        String verdict = "";
                        System.out.print("Would you like to make any more changes? ");
                        verdict = input.nextLine().trim().toLowerCase();
                        if (verdict.equals("no") || verdict.equals("n")) {
                            flag = false;
                        } else if (verdict.equals("yes") || verdict.equals("y")) {
                            String whatToDo = "";
                            System.out.print("What would you like to do to the equation (Replace / remove / add)? ");
                            whatToDo = input.nextLine().trim().toLowerCase();
                            if (whatToDo.equals("add")) {
                                int addPosition;
                                System.out.print("What position would you like to add something ? ");
                                try {
                                    addPosition = input.nextInt();
                                    input.nextLine();
                                } catch (InputMismatchException ex) {
                                    input.nextLine();
                                    System.out.print("What position would you like to add? ");
                                    input.nextLine();
                                    System.out.println("\nChanging Equation Error: Information given is invalid!");
                                    continue;
                                }
                                String addAtPosition;
                                System.out.print("What would you like to add? ");
                                addAtPosition = input.nextLine().trim();
                                Equation temp = historyStack.addSomething(1, addPosition,
                                        addAtPosition);
                                if (temp == null) {
                                    System.out.println("\nChanging Equation Error: Can't modify the equation with " +
                                            "given info!");
                                    continue;
                                } else {
                                    changed = temp;
                                }
                            } else if (whatToDo.equals("remove")) {
                                int removePosition;
                                System.out.print("What position would you like to remove? ");
                                try {
                                    removePosition = input.nextInt();
                                    input.nextLine();
                                } catch (InputMismatchException ex) {
                                    input.nextLine();
                                    System.out.println("\nChanging Equation Error: Information given is invalid!");
                                    continue;
                                }
                                Equation temp = historyStack.removeSomething(1, removePosition);
                                if (temp == null) {
                                    System.out.println("\nChanging Equation Error: Can't modify the equation with " +
                                            "given info!");
                                    continue;
                                } else {
                                    changed = temp;
                                }
                            } else if (whatToDo.equals("replace")) {
                                int changePosition;
                                System.out.print("What position would you like to change? ");
                                try {
                                    changePosition = input.nextInt();
                                    input.nextLine();
                                } catch (InputMismatchException ex) {
                                    input.nextLine();
                                    System.out.print("What would you like to replace it with? ");
                                    input.nextLine();
                                    System.out.println("\nChanging Equation Error: Information given is invalid!");
                                    continue;
                                }
                                String changeWith;
                                System.out.print("What would you like to replace it with? ");
                                changeWith = input.nextLine().trim();
                                Equation temp = historyStack.replaceSomething(1, changePosition,
                                        changeWith);
                                changed = temp;
                                if (temp == null) {
                                    System.out.println("\nChanging Equation Error: Can't modify the equation with " +
                                            "given info!");
                                    continue;
                                } else {
                                    changed = temp;
                                }
                            } else {
                                System.out.println("\nChanging Equation Error: Invalid information given!\n");
                                continue;
                            }
                        } else {
                            System.out.println("\nChanging Equation Error: Invalid information given!\n");
                            continue;
                        }
                        historyStack.pop();
                    }
                    if (!changed.getBalanced())
                        System.out.println("The equation is not balanced!\n");
                    else
                        System.out.println("\nThe equation is balanced and the answer is "
                                + df.format(changed.getAnswer()) + "!\n");
                    Equation modded = new Equation(changed.getEquation());
                    historyStack.push(modded);
                    break;
                case ("B"):
                    System.out.println("\n" + historyStack.lastEquation() + "\n");
                    break;
                case ("P"):
                    System.out.println("\n" + historyStack);
                    break;
                case ("U"):
                    try {
                        historyStack.undo();
                    } catch (Exception ex) {
                        System.out.println("\n" + ex.getLocalizedMessage() + "\n");
                        continue;
                    }
                    System.out.println("\nEquation " + historyStack.getUndos().peek().getEquation() + " undone!\n");
                    break;
                case ("R"):
                    try {
                        historyStack.redo();
                    } catch (Exception ex) {
                        System.out.println("\n" + ex.getLocalizedMessage() + "\n");
                        continue;
                    }
                    System.out.println("\nRedoing equation " + historyStack.peek().getEquation() + "!\n");
                    break;
                case ("C"):
                    historyStack.clearHistory();
                    System.out.println("\nResetting the calculator ... DONE!\n");
                    break;
                case ("Q"):
                    System.out.println("\nProgram terminating normally!");
                    break;
                default:
                    System.out.println("\nNot a valid operation!\n");
            }
        }
    }

    /**
     * Prints the menu of operation that the user can do in order to simulate using the Calculator.
     */
    public static void printTable() {
        System.out.println("[A] Add new Equation");
        System.out.println("[F] Change equation from history");
        System.out.println("[B] Print previous equation");
        System.out.println("[P] Print full history");
        System.out.println("[U] Undo");
        System.out.println("[R] Redo");
        System.out.println("[C] Clear History");
        System.out.println("[Q] Quit");
    }
}
