//Rezvan Nafee
//112936468
//Recitation 04

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class represents an equation the class that will be used to find the prefix,infix,binary, hexadecimal, and the
 * equation given by the user.
 *
 * @author Rezvan Nafee
 * @ID 112936468
 * @Recitation Section 04
 */
public class Equation {
    /**
     * Decimal format will help display the answer rounded to the nearest 3 decimal places.
     */
    public DecimalFormat df = new DecimalFormat("0.000");
    private String equation;
    private String prefix;
    private String postfix;
    private double answer;
    private String binary;
    private String hex;
    private boolean balanced;

    /**
     * Creates a default Equation
     */
    public Equation() {
        this.prefix = "N/A";
        this.postfix = "N/A";
        this.answer = 0;
        this.binary = "0";
        this.hex = "0";
    }

    /**
     * Creates an Equation object by taking a String entered by the user to be evaluated and it throws an Exception
     * when the inputted equation cannot be computed or mathematically be operated on. Furthermore, the inputted String
     * is in infix notation.
     *
     * @param equation A mathematical expression entered by the user to be evaluated.
     * @throws Exception
     */
    public Equation(String equation) throws Exception {
        this.equation = equation;
        this.balanced = isBalanced();
        if (!balanced) {
            this.prefix = "N/A";
            this.postfix = "N/A";
            this.answer = 0;
            this.binary = "0";
            this.hex = "0";
        } else {
            this.postfix = postfix();
            try {
                this.answer = evaluateExpression();
            } catch (NumberFormatException ex) {
                this.prefix = "N/A";
                this.postfix = "N/A";
                this.answer = 0;
                this.binary = "0";
                this.hex = "0";
                this.balanced = false;
            } catch (NullPointerException ex) {
                this.prefix = "N/A";
                this.postfix = "N/A";
                this.answer = 0;
                this.binary = "0";
                this.hex = "0";
                this.balanced = false;
            }
            this.postfix = postfix();
            this.prefix = prefix();
            this.binary = decToBin((int) Math.round(answer));
            this.hex = decToHex((int) Math.round(answer));
        }
    }

    /**
     * Returns true if the number of left-parenthesis within in the give expression is the same as the number of the
     * right-parenthesis. If there is not the equivalent amount, then the function returns false.
     * In addition, it also determines if the equation entered can be mathematically operated!
     *
     * @return
     */
    public boolean isBalanced() {
        for (int i = 0; i < equation.length(); i++) {

        }
        for (int i = 0; i < equation.length(); i++) {
            char key = equation.charAt(i);
            if (key == '=') {
                balanced = false;
                return false;
            }
        }
        ArrayList<Character> checkEquation = new ArrayList<>();
        for (int i = 0; i < equation.length(); i++) {
            char key = equation.charAt(i);
            switch (key) {
                case ('('):
                    checkEquation.add(key);
                    break;
                case (')'):
                    int index = checkEquation.lastIndexOf('(');
                    checkEquation.remove(index);
                    break;
            }
        }
        if (checkEquation.isEmpty()) {
            balanced = true;
            return true;
        } else {
            balanced = false;
            return false;
        }
    }

    /**
     * Returns a String representing the binary of a given decimal number.
     *
     * @param number The decimal number that would like to be converted into binary.
     * @return The binary String representation of the given number.
     */
    public String decToBin(int number) {
        if (number == 0)
            return "0";
        if (number < 0) {
            number = number * -1;
            String bin = "-" + decToBin(number);
            return bin;
        }
        String preBinary = "";
        while (number > 0) {
            int num;
            if (number % 2 == 0)
                num = 0;
            else
                num = 1;
            number = number / 2;
            preBinary += num;
        }
        String bin = "";
        for (int i = preBinary.length() - 1; i >= 0; i--) {
            bin += String.valueOf(preBinary.charAt(i));
        }
        return bin;
    }

    /**
     * Returns a String representing the hexadecimal of a given decimal number.
     *
     * @param number The decimal number that would like to be converted into hexadecimal.
     * @return The hexadecimal String representation of the given decimal number
     */
    public String decToHex(int number) {
        if (number == 0)
            return "0";
        if (number < 0) {
            number = number * -1;
            String hex = "-" + decToHex(number);
            return hex;
        }
        String preHex = "";
        while (number > 0) {
            int num = number % 16;
            if (num < 10)
                preHex += num;
            else {
                switch (num) {
                    case (10):
                        preHex += "A";
                        break;
                    case (11):
                        preHex += "B";
                        break;
                    case (12):
                        preHex += "C";
                        break;
                    case (13):
                        preHex += "D";
                        break;
                    case (14):
                        preHex += "E";
                        break;
                    case (15):
                        preHex += "F";
                        break;
                }
            }
            number /= 16;
        }
        String hex = "";
        for (int i = preHex.length() - 1; i >= 0; i--) {
            hex += String.valueOf(preHex.charAt(i));
        }
        return hex;
    }

    /**
     * Returns a String representation of the postfix notation of the equation.
     *
     * @return The String representation of the postfix of a given expression.
     */
    public String postfix() {
        String postfix = "";
        EquationStack operator = new EquationStack();
        for (int i = 0; i < equation.length(); i++) {
            char key = equation.charAt(i);
            if (key == ' ')
                continue;
            if (Character.isDigit(key)) {
                postfix += key;
                for (int j = i + 1; j < equation.length(); j++) {
                    char temp = equation.charAt(j);
                    if (Character.isDigit(temp)) {
                        postfix += temp;
                        i++;
                    } else {
                        break;
                    }
                }
                postfix += " ";
            } else if (key == '(') {
                operator.push(String.valueOf(key));
            } else if (key == ')') {
                while (!operator.isEmpty() && operator.peek().toCharArray()[0] != '(') {
                    postfix += operator.pop() + " ";
                }
                operator.pop();
            } else {
                while (!operator.isEmpty() && precedenceOperation(key) <
                        precedenceOperation(operator.peek().toCharArray()[0])) {
                    postfix += operator.pop() + " ";
                }
                operator.push(String.valueOf(key));
            }
        }
        while (!operator.isEmpty()) {
            postfix += operator.pop().toCharArray()[0] + " ";
        }
        return postfix;
    }

    /**
     * Returns a String representation of the prefix notation of the equation.
     *
     * @return The String representation of the postfix of a given expression.
     */
    public String prefix() {
        EquationStack stack = new EquationStack();
        if(postfix.length() == 0)
            return "N/A";
        String[] arr = postfix.split(" ");
        for (int i = 0; i < arr.length; i++) {
            String key = arr[i];
            if (checkOperationSymbol(key.charAt(0))) {
                String num2 = stack.pop();
                String num1 = stack.pop();
                stack.push(String.valueOf(key + " " + num1 + " " + num2 + " "));
            } else {
                stack.push(key);
            }
        }
        return stack.pop();
    }

    /**
     * Returns the answer of the equation by using the postfix notation.
     *
     * @return Returns the answer of the equation
     */
    public double evaluateExpression() {
        EquationStack operand = new EquationStack();
        for (int i = 0; i < postfix.length(); i++) {
            if (postfix.charAt(i) == ' ')
                continue;
            else if (Character.isDigit(postfix.charAt(i))) {
                String number = "";
                number += postfix.charAt(i);
                int counter = 0;
                for (int j = i + 1; j < postfix.length(); j++) {
                    if (Character.isDigit(postfix.charAt(j))) {
                        number += postfix.charAt(j);
                        counter++;
                    } else {
                        i += counter;
                        break;
                    }
                }
                operand.push(number);
            } else {
                double num2 = Double.parseDouble(operand.pop());
                double num1 = Double.parseDouble(operand.pop());
                char operation = postfix.charAt(i);
                if (performOperation(operation, num1, num2) == null)
                    return -1.0;
                operand.push(performOperation(operation, num1, num2));
            }
        }
        ;
        return Double.parseDouble(operand.pop());
    }

    /**
     * Checks if the symbol given is an operator.
     *
     * @param symbol
     * @return Returns true if the the symbol is a valid operator
     * Return false if it is not a valid operator
     */
    public boolean checkOperationSymbol(char symbol) {
        if (symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/' || symbol == '%' || symbol == '('
                || symbol == ')' || symbol == '^')
            return true;
        else
            return false;
    }

    /**
     * Returns the String representation of an evaluated mathematical expression.
     *
     * @param c        The operator
     * @param operand1 One of the operands
     * @param operand2 The other operand
     * @return Returns the string representation of the answer of an expression.
     */
    public String performOperation(char c, double operand1, double operand2) {
        if (c == '+')
            return String.valueOf(operand1 + operand2);
        else if (c == '-')
            return String.valueOf(operand1 - operand2);
        else if (c == '*')
            return String.valueOf(operand1 * operand2);
        else if (c == '%')
            return String.valueOf(operand1 % operand2);
        else if (c == '^')
            return String.valueOf(Math.pow(operand1, operand2));
        else if (c == '/') {
            if (operand2 == 0)
                return String.valueOf("c");
            else
                return String.valueOf(operand1 / operand2);
        }
        return "0";
    }

    /**
     * Returns the priority of the which operator must be done first.
     *
     * @param c The operand
     * @return Returns the priority of the operator.
     * 3 --> The highest priority
     * 1 --> The lowest priority.
     * 0 --> Not a priority (Symbol is not an operator).
     */
    public int precedenceOperation(char c) {
        switch (c) {
            case ('^'):
                return 3;
            case ('*'):
            case ('/'):
            case ('%'):
                return 2;
            case ('+'):
            case ('-'):
                return 1;
            default:
                return 0;
        }
    }

    /**
     * Returns the the answer of the Equation
     *
     * @return The answer to the Equation.
     */
    public double getAnswer() {
        return answer;
    }

    /**
     * Returns the String representation of the binary of the answer
     *
     * @return The binary representation of the answer of the equation.
     */
    public String getBinary() {
        return binary;
    }

    /**
     * Returns the String representation infix notation of the equation.
     *
     * @return The equation in infix notation.
     */
    public String getEquation() {
        return equation;
    }

    /**
     * Returns the String representation of the hexadecimal of the answer
     *
     * @return The hexadecimal representation of the answer of the equation.
     */
    public String getHex() {
        return hex;
    }

    /**
     * Returns the boolean condition of whether or not equation has an equal amount of left-parenthesis  and
     * right-parenthesis.
     *
     * @return The boolean condition
     */
    public boolean getBalanced() {
        return this.balanced;
    }

    /**
     * Returns the String representation of the postfix notation of the equation.
     *
     * @return The postfix notation of the equation.
     */
    public String getPostfix() {
        return postfix;
    }

    /**
     * Returns the String representation of the prefix notation of the equation.
     *
     * @return The prefix notation of the equation.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets the answer of the equation to a specified value
     *
     * @param answer The specified answer
     */
    public void setAnswer(double answer) {
        this.answer = answer;
    }

    /**
     * Sets the String representation of the binary of the answer to a specified String representation of binary.
     *
     * @param binary The specified String representation of binary.
     */
    public void setBinary(String binary) {
        this.binary = binary;
    }

    /**
     * Sets the equation to a specified equation.
     *
     * @param equation The specified equation.
     */
    public void setEquation(String equation) {
        this.equation = equation;
    }

    /**
     * Sets the String representation of the hexadecimal of the answer to a specified String representation
     * of hexadecimal.
     *
     * @param hex The specified String representation of hexadecimal.
     */
    public void setHex(String hex) {
        this.hex = hex;
    }

    /**
     * Sets the String representation of the postfix notation to a specified postfix notation.
     *
     * @param postfix The specified postfix notation.
     */
    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    /**
     * Sets the String representation of the prefix notation to a specified postfix notation.
     *
     * @param prefix The specified prefix notation.
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @param balanced
     */
    public void setBalanced(boolean balanced) {
        this.balanced = balanced;
    }

    /**
     * Returns String representation of an Equation object in  a formatted table that highlights the prefix, postfix,
     * answer, binary, and hexadecimal.
     *
     * @return The String representation of Equation.
     */
    public String toString() {
        String result = "";
        String header = String.format("%-40s%-50s%-50s%-30s%-30s%-30s", "Equation", "Prefix", "Postfix", "Answer",
                "Binary"
                , "Hexadecimal");
        String divider = ("\n--------------------------------------------------------------------------------------" +
                "------------------------------------------------------------------------------------------------" +
                "------" +
                "---" +
                "----------------------\n");
        String info = String.format("%-40s%-50s%-50s%-30s%-30s%-30s", this.equation.trim(), this.prefix.trim()
                , this.postfix.trim(),
                df.format(this.answer), this.binary, this.hex);
        result += header + divider + info;
        return result;
    }


}
