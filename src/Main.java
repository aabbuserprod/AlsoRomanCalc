import java.util.Scanner;
class Main {
    enum RomanNumeral {
        I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10),
        XI(11), XII(12), XIII(13), XIV(14), XV(15), XVI(16), XVII(17), XVIII(18), XIX(19), XX(20),
        XXI(21), XXII(22), XXIV(24), XXV(25), XXVII(27), XXVIII(28), XXX(30),
        XXXI(31), XXXII(32), XXXIV(34), XXXV(35), XXXVI(36), XL(40),
        XLII(42), XLV(45), XLVIII(48), XLIX(49), L(50),
        LIV(54), LVI(56), LX(60),
        LXIII(63), LXIV(64), LXX(70),
        LXXII(72), LXXX(80),
        LXXXI(81), XC(90),
        C(100);

        private String value;


        RomanNumeral(int value) {
            this.value = String.valueOf(value);
        }


        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Type an equation and hit 'Enter'. Here some tips for You:
                1. The calculator operates with integers from 1 to 10.
                2. Use Roman or Arabic numerals, you cannot mix the numeral systems.
                3. Only two numbers can be added, subtracted, multiplied or divided.
                4. Separate operand and the operator with a single space.""");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Result: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        //System.out.println(RomanNumeral.XLII.getValue());

    }

    public static String calc(String input) {

        // Split input to operands and operator
        String[] chunks = input.split("\\s");
        if (chunks.length != 3) {
            throw new IllegalArgumentException("Illegal format of equation. Separate operand and the operator with a single space");
        } else {
            // Check input for correct num of spaces
            String num1 = chunks[0];
            String num2 = chunks[2];
            if (chunks[0].matches("^[a-zA-Z]*$") && chunks[2].matches("\\d+") || chunks[0].matches("\\d+") && chunks[2].matches("^[a-zA-Z]*$")) {
                throw new IllegalArgumentException("Illegal format of operands. You cannot mix Arabic and Roman numeral systems");
            } else {
                // If it's a Roman Case
                if (chunks[0].matches("^[a-zA-Z]*$") && chunks[2].matches("^[a-zA-Z]*$")) {
                    // Convert num1, num2 via enum RomanNumeral to integers
                    int a, b;
                    a = Integer.parseInt(RomanNumeral.valueOf(num1).getValue());
                    b = Integer.parseInt(RomanNumeral.valueOf(num2).getValue());

                    // Check operands, condition - from 1 to 10
                    if (a < 1 || a > 10 || b < 1 || b > 10) {
                        throw new IllegalArgumentException("The calculator operates with integers from 1 to 10");
                    } else {

                        // Select and perform operation by operand (chunk[1])
                        int result = switch (chunks[1]) {
                            case "+" -> a + b;
                            case "-" -> a - b;
                            case "*" -> a * b;
                            case "/" -> a / b;
                            default ->
                                    throw new IllegalArgumentException("Check operator (Calculator can add, subtract, multiply or divide only)");
                        };

                        if (result < 1 && chunks[0].matches("\\w+") && chunks[2].matches("\\w+")) {
                            throw new IllegalArgumentException("The result with Roman numerals cannot be less then 1");
                        } else {
                            return String.valueOf(result);
                        }
                    }
                }
                // It's an Arabic
                else {
                    int a, b;
                    a = Integer.parseInt(num1);
                    b = Integer.parseInt(num2);

                    // Check operands, condition - from 1 to 10
                    if (a < 1 || a > 10 || b < 1 || b > 10) {
                        throw new IllegalArgumentException("The calculator operates with integers from 1 to 10");
                    }

                    // Select and perform operation by operand (chunk[1])
                    int result = switch (chunks[1]) {
                        case "+" -> a + b;
                        case "-" -> a - b;
                        case "*" -> a * b;
                        case "/" -> a / b;
                        default ->
                                throw new IllegalArgumentException("Check operator (Calculator can add, subtract, multiply or divide only)");
                    };

                    return String.valueOf(result);
                }
            }
        }
    }
}