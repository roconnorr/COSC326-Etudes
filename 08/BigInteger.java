/**
 * BigInteger.java (Java7+)
 * Represents arbitrary precision integers and provides operations
 * for them.
 *
 * Rory O'Connor - 9825774
 * Joe Gasparich - 2373980
 */
import java.util.*;

public class BigInteger {

    //-1 = negative, 0 = 0, 1 = positive
    private final int sign;

    //stores each digit
    private final int[] digits;

    //constants for 0 and 1
    private static final BigInteger ZERO = new BigInteger("0");
    private static final BigInteger ONE = new BigInteger("1");

    public static void main(String[] args) {
        ArrayList<InputScenario> inputs = new ArrayList<>();
        Scanner scan = new Scanner(System.in);

        //parse input
        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            if (input.length() != 0 && input.charAt(0) != '#') {
                String[] tokens = input.split(" ");
                try {
                    //if there are too many or too few strings in the array
                    //it doesn't fit the input format
                    if (tokens.length > 3 || tokens.length < 3) {
                        throw new NumberFormatException();
                    }

                    //check if any of the inputs are empty
                    if(tokens[0].isEmpty() || tokens[1].isEmpty() || tokens[2].isEmpty()){
                        throw new NumberFormatException();
                    }

                    String operation = tokens[1];
                    String aNum = "";
                    String bNum = "";

                    //check for negative sign and remove if it exists
                    if (tokens[0].charAt(0) == '-') {
                        aNum += "-";
                        tokens[0] = tokens[0].substring(1);
                    }
                    //check that each character in the string is a number
                    for (int i = 0; i < tokens[0].length(); i++) {
                        if (Character.isDigit(tokens[0].charAt(i))) {
                            aNum += tokens[0].charAt(i);
                        } else {
                            throw new NumberFormatException();
                        }
                    }

                    //check for negative sign and remove if it exists
                    if (tokens[2].charAt(0) == '-') {
                        bNum += "-";
                        tokens[2] = tokens[2].substring(1);
                    }
                    //check that each character in the string is a number
                    for (int i = 0; i < tokens[2].length(); i++) {
                        if (Character.isDigit(tokens[2].charAt(i))) {
                            bNum += tokens[2].charAt(i);
                        } else {
                            throw new NumberFormatException();
                        }
                    }

                    //nput is valid, add to the inputs array
                    inputs.add(new InputScenario(new BigInteger(aNum),
                            new BigInteger(bNum), operation, true, ""));
                } catch (NumberFormatException ex) {
                    //adds invalid input scenario if an error was encountered
                    inputs.add(new InputScenario(
                            ZERO, ZERO, " ", false, input));
                }
            }
        }
        scan.close();

        //process each input
        for (InputScenario i : inputs) {
            if (i.isValid) {
                switch (i.operation) {
                    case "+":
                        System.out.println(BigInteger.toString(i.a) + " + "
                                + BigInteger.toString(i.b));

                        System.out.println("# " + BigInteger.toString(
                                BigInteger.add(i.a, i.b)) + "\n");
                        break;
                    case "-":
                        System.out.println(BigInteger.toString(i.a) + " - "
                                + BigInteger.toString(i.b));
                        System.out.println("# " + BigInteger.toString(
                                BigInteger.subtract(i.a, i.b)) + "\n");
                        break;

                    case "*":
                        System.out.println(BigInteger.toString(i.a) + " * "
                                + BigInteger.toString(i.b));
                        System.out.println("# " + BigInteger.toString(
                                BigIntegerExtension.multiply(i.a, i.b)) + "\n");
                        break;

                    case "/":
                        System.out.println(BigInteger.toString(i.a) + " / "
                                + BigInteger.toString(i.b));
                        BigInteger[] divResult
                                = BigIntegerExtension.divide(i.a, i.b);
                        System.out.println("# " + BigInteger.toString(
                                divResult[0]) + " "
                                + BigInteger.toString(divResult[1]) + "\n");
                        break;

                    case "gcd":
                        System.out.println(BigInteger.toString(i.a) + " gcd "
                                + BigInteger.toString(i.b));
                        System.out.println("# " + BigInteger.toString(
                                BigIntegerExtension.gcd(i.a, i.b)) + "\n");
                        break;

                    case "<":
                        System.out.println(BigInteger.toString(i.a) + " < "
                                + BigInteger.toString(i.b));
                        if (BigInteger.compareTo(i.a, i.b) == -1) {
                            System.out.println("# true \n");
                        } else {
                            System.out.println("# false \n");
                        }
                        break;

                    case ">":
                        System.out.println(BigInteger.toString(i.a) + " > "
                                + BigInteger.toString(i.b));
                        if (BigInteger.compareTo(i.a, i.b) == 1) {
                            System.out.println("# true \n");
                        } else {
                            System.out.println("# false \n");
                        }
                        break;
                    case "=":
                        System.out.println(BigInteger.toString(i.a) + " = "
                                + BigInteger.toString(i.b));
                        if (BigInteger.compareTo(i.a, i.b) == 0) {
                            System.out.println("# true \n");
                        } else {
                            System.out.println("# false \n");
                        }
                        break;
                    default:
                        System.out.println(BigInteger.toString(i.a)
                                + i.operation + BigInteger.toString(i.b));
                        System.out.println("# Syntax error" + "\n");
                        break;
                }
            } else {
                //if the input was not valid, print raw input and error message
                System.out.println(i.invalidInputString);
                System.out.println("# Syntax error" + "\n");
            }
        }
    }

    /*
     * Constructor for turning a valid string of digits into a BigInteger
     */
    public BigInteger(String number) {
        //check the sign
        if (number.charAt(0) == '-') {
            sign = -1;
            //remove the sign character
            number = new StringBuilder(number).deleteCharAt(0).toString();
            //special case for 0
        } else if (number == "0") {
            sign = 0;
        } else {
            sign = 1;
        }

        //reverse string so least significant numbers are at the start of array
        number = new StringBuilder(number).reverse().toString();

        //instantiate storage array with correct length
        digits = new int[number.length()];

        //attempts to parse each character in the string as an int
        //if successfull all ints are added to the number storage array
        try {
            for (int i = 0; i < number.length(); i++) {
                if (Character.isDigit(number.charAt(i))) {
                    digits[i] = Character.getNumericValue(number.charAt(i));
                } else {
                    throw new NumberFormatException();
                }
            }
        } catch (NumberFormatException ex) {
            System.out.println("Failed to parse integer");
        }
    }

    /*
     * Constructor for initializing a BigInteger with a new sign
     */
    public BigInteger(BigInteger number, int newSign) {
        this.digits = number.digits;
        this.sign = newSign;
    }

    /*
     * Convenience constructor for initalizing with int array and sign
     * private because it does not perform validation checks.
     */
    private BigInteger(int[] newDigits, int newSign) {
        this.digits = newDigits;
        this.sign = newSign;
    }

    /*
     * Adds two BigIntegers together
     */
    public static BigInteger add(BigInteger a, BigInteger b) {
        int newSign = 1;
        // if either number is 0 return the other
        if (a.sign == 0) {
            return b;
        }
        if (b.sign == 0) {
            return a;
        }

        //handle negative numbers
        if (a.sign == -1 && b.sign == -1) {
            //add negative sign
            newSign = -1;
        } else if (a.sign == 1 && b.sign == -1) {
            //subtract with inverted b
            return BigInteger.subtract(a, new BigInteger(b.digits, 1));
        } else if (a.sign == -1 && b.sign == 1) {
            //subtract positive a from b
            return BigInteger.subtract(b, new BigInteger(a.digits, 1));
        }

        int[] digits1, digits2;

        //make the first array the longer one
        if (BigInteger.compareTo(a, b) == 1) {
            digits1 = a.digits;
            digits2 = b.digits;
        } else {
            digits1 = b.digits;
            digits2 = a.digits;
        }

        int remainder = 0;

        int[] resultDigits = new int[digits1.length + 1];

        for (int i = 0; i < digits1.length; i++) {
            //digit1 is ensured to be larger so a check is not required
            int digit1 = digits1[i];
            int digit2;

            //if there are no more digits in the smaller array,
            //pad with 0
            if (i >= digits2.length) {
                digit2 = 0;
            } else {
                digit2 = digits2[i];
            }

            //perform the add
            int sum = digit1 + digit2 + remainder;

            //if the result is too large, set remainder
            if (sum > 9) {
                remainder = 1;
            } else {
                remainder = 0;
            }

            //fit number into result
            resultDigits[i] = sum % 10;
        }

        //add the final remainder
        resultDigits[digits1.length] = remainder;

        //remove leading zeros
        if (resultDigits[resultDigits.length - 1] == 0) {
            resultDigits = Arrays.copyOfRange(resultDigits, 0, digits1.length);
        }

        //handle 0 sign
        if (resultDigits.length == 1 && resultDigits[0] == 0) {
            newSign = 0;
        }

        return new BigInteger(resultDigits, newSign);
    }

    /*
     * Subtracts one BigInteger from another.
     */
    public static BigInteger subtract(BigInteger a, BigInteger b) {
        //store the sign of the result (positive default)
        int resultSign = 1;

        //if either number is 0 return the other number intact
        if (a.sign == 0) {
            return b;
        }
        if (b.sign == 0) {
            return a;
        }

        //handle negative numbers
        if (a.sign == -1 && b.sign == -1) {
            //add negative sign
            return BigInteger.subtract(new BigInteger(b.digits, 1),
                    new BigInteger(a.digits, 1));
        } else if (a.sign == -1 && b.sign == 1) {
            //add both as negative
            return BigInteger.add(a, new BigInteger(b.digits, -1));
        } else if (a.sign == 1 && b.sign == -1) {
            //add positive b and a
            return BigInteger.add(new BigInteger(b.digits, 1), a);
        }

        //ensure digits1 is the longer array
        int[] digits1, digits2;
        if (BigInteger.compareTo(a, b) == 1) {
            digits1 = a.digits;
            digits2 = b.digits;
        } else if (BigInteger.compareTo(a, b) == -1) {
            digits1 = b.digits;
            digits2 = a.digits;
            resultSign = -1;
        } else {
            return ZERO;
        }

        //Store results
        int[] resultDigits = new int[digits1.length];

        // Initialize carry.
        int carry = 0;

        // Loop through all the digits, one by one.
        for (int i = 0; i < resultDigits.length; i++) {
            int temp = digits1[i];

            // Subtract the digit from the smaller array if it exists
            if (i < digits2.length) {
                temp -= digits2[i];
            }

            // Subtract the carry from the last operation.
            temp -= carry;
            carry = 0;

            // If the result is negative, borrow
            if (temp < 0) {
                temp = temp + 10;
                carry = 1;
            }

            // Set the result
            resultDigits[i] = temp;
        }

        //remove leading zeros
        if (resultDigits[resultDigits.length - 1] == 0) {
            resultDigits = Arrays.copyOfRange(resultDigits, 0,
                    resultDigits.length - 1);
        }

        //handle 0 sign
        if (resultDigits.length == 1 && resultDigits[0] == 0) {
            resultSign = 0;
        }

        return new BigInteger(resultDigits, resultSign);
    }

    /*
     * Performs integer halving operation on BigInteger
     * e.g. 5/2 = 2
     */
    public static BigInteger truncatedHalve(BigInteger a) {
        int[] resultDigits = new int[a.digits.length];
        int carry = 0;

        for (int i = a.digits.length - 1; i > -1; i--) {
            int temp = a.digits[i] / 2;

            if (a.digits[i] % 2 == 1 && i != 0) {
                resultDigits[i - 1] = 5;
            }

            resultDigits[i] += temp;
        }

        //remove leading zeros
        if (resultDigits[resultDigits.length - 1] == 0) {
            resultDigits = Arrays.copyOfRange(resultDigits, 0,
                    resultDigits.length - 1);
        }
        int newSign = a.sign;
        if (resultDigits.length == 1 && resultDigits[0] == 0) {
            newSign = 0;
        }
        return new BigInteger(resultDigits, newSign);
    }

    /*
     * Compares one BigInteger to another
     * returns -1 if a < b
     */
    public static int compareTo(BigInteger a, BigInteger b) {
        int[] digits1 = a.digits;
        int[] digits2 = b.digits;

        int aSign = a.sign;
        int bSign = b.sign;

        if (a.digits.length == 1 && a.digits[0] == 0) {
            aSign = 0;
        }
        if (b.digits.length == 1 && b.digits[0] == 0) {
            bSign = 0;
        }

        if (aSign > bSign) {
            return 1;
        }
        if (bSign > aSign) {
            return -1;
        }

        if (a.digits.length > b.digits.length) {
            return aSign;
        } else if (a.digits.length == b.digits.length) {
            int count = 0;
            while (a.digits[a.digits.length - 1 - count]
                    == b.digits[b.digits.length - 1 - count]) {
                if (a.digits.length - 1 - count == 0) {
                    if (aSign == bSign) {
                        return 0;
                    } else {
                        return aSign;
                    }
                }
                count++;
            }
            if (a.digits[a.digits.length - 1 - count]
                    > b.digits[b.digits.length - 1 - count]) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /*
     * Returns a string representation of a BigInteger
     * reverses the internal representation to print in the
     * correct order
     */
    public static String toString(BigInteger bigInt) {
        StringBuilder sb = new StringBuilder();

        if (bigInt.sign == -1) {
            sb.append("-");
        }

        for (int i = bigInt.digits.length - 1; i > -1; i--) {
            sb.append(bigInt.digits[i]);
        }

        return sb.toString();
    }
}

class BigIntegerExtension {

    //constants for 0 and 1
    private static final BigInteger ZERO = new BigInteger("0");
    private static final BigInteger ONE = new BigInteger("1");

    /*
     * Multiplies two BigIntegers together
     */
    public static BigInteger multiply(BigInteger a, BigInteger b) {
        int sign = BigInteger.compareTo(a, ZERO)
                * BigInteger.compareTo(b, ZERO);
        int bSign = BigInteger.compareTo(b, ZERO);

        BigInteger result = ZERO;

        //define addition through adding a to result b times
        for (int i = 0; BigInteger.compareTo(b,
                new BigInteger(Integer.toString(i))) == bSign;
                i += BigInteger.compareTo(b,
                        new BigInteger(Integer.toString(i)))) {
            result = BigInteger.add(result, a);
        }

        //handle 0 sign
        if (BigInteger.compareTo(result, ZERO) == 0) {
            sign = 0;
        }
        //set result sign and return
        result = new BigInteger(result, sign);
        return result;
    }

    /*
     * Divides one BigInteger by another
     */
    public static BigInteger[] divide(BigInteger a, BigInteger b) {
        int resultSign = (BigInteger.compareTo(a, ZERO)
                * BigInteger.compareTo(b, ZERO));

        if (BigInteger.compareTo(b, ZERO) == 0) {
            return null;
        }

        BigInteger result = ZERO;
        BigInteger aLocal = a;
        BigInteger bLocal = b;

        aLocal = new BigInteger(a, 1);
        bLocal = new BigInteger(b, 1);

        //define division in terms of subtraction by counting how 
        //many times a can be subtracted from b and storing the remainder
        while (BigInteger.compareTo(aLocal, bLocal) >= 0) {
            aLocal = BigInteger.subtract(aLocal, bLocal);
            result = BigInteger.add(result, ONE);
        }

        BigInteger remainder = aLocal;

        int remainderSign = resultSign;

        if (BigInteger.compareTo(result, ZERO) == 0) {
            resultSign = 0;
        }
        if (BigInteger.compareTo(remainder, ZERO) == 0) {
            remainderSign = 0;
        }

        remainder = new BigInteger(remainder, resultSign);
        result = new BigInteger(result, remainderSign);

        //return a BigInteger array representing the result and remainder
        BigInteger[] returnVal = new BigInteger[2];
        returnVal[0] = result;
        returnVal[1] = remainder;

        return returnVal;
    }

    /*
     * Finds the greatest common divisor of two BigIntegers
     */
    public static BigInteger gcd(BigInteger a, BigInteger b) {
        BigInteger start = ONE;
        switch (BigInteger.compareTo(a, b)) {
            case 1:
                BigInteger[] resultA = BigIntegerExtension.divide(a, b);
                if (BigInteger.compareTo(resultA[1], ZERO) == 0) {
                    return b;
                }
                start = BigInteger.truncatedHalve(b);
                break;
            case -1:
                BigInteger[] resultB = BigIntegerExtension.divide(b, a);
                if (BigInteger.compareTo(resultB[1], ZERO) == 0) {
                    return a;
                }
                start = BigInteger.truncatedHalve(a);
                break;
            case 0:
                return a;
            default:
                break;
        }

        //divide both numbers by half the smaller number
        //decrement this number until both can be divided with no remainder
        while (BigInteger.compareTo(start, ZERO) == 1) {
            BigInteger[] aResult = BigIntegerExtension.divide(a, start);
            BigInteger[] bResult = BigIntegerExtension.divide(b, start);
            if (BigInteger.compareTo(aResult[1], ZERO) == 0
                    && BigInteger.compareTo(bResult[1], ZERO) == 0) {
                return start;
            }
            start = BigInteger.subtract(start, ONE);
        }

        return ONE;
    }
}

/*
 * Stores input scenarios
 */
class InputScenario {

    BigInteger a;
    BigInteger b;
    String operation;
    Boolean isValid;
    String invalidInputString;

    public InputScenario(BigInteger a, BigInteger b, String op,
            Boolean isValid, String invalid) {
        this.a = a;
        this.b = b;
        this.operation = op;
        this.isValid = isValid;
        this.invalidInputString = invalid;
    }
}