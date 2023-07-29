import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
class Main {
    enum RomanNumeral {

        I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10),
        XI(11), XII(12), XIII(13), XIV(14), XV(15), XVI(16), XVII(17), XVIII(18), XIX(19), XX(20),
        XXI(21), XXII(22), XXIII(23), XXIV(24), XXV(25), XXVI(26), XXVII(27), XXVIII(28), XXIX(29), XXX(30),
        XXXI(31), XXXII(32), XXXIII(33), XXXIV(34), XXXV(35), XXXVI(36), XXXVII(37), XXXVIII(38), XXXIX(39), XL(40),
        XLI(41), XLII(42), XLIII(43), XLIV(44), XLV(45), XLVI(46), XLVII(47), XLVIII(48), XLIX(49), L(50),
        LI(51), LII(52), LIII(53), LIV(54), LV(55), LVI(56), LVII(57), LVIII(58), LIX(59), LX(60),
        LXI(61), LXII(62), LXIII(63), LXIV(64), LXV(65), LXVI(66), LXVII(67), LXVIII(68), LXIX(69), LXX(70),
        LXXI(71), LXXII(72), LXXIII(73), LXXIV(74), LXXV(75), LXXVI(76), LXXVII(77), LXXVIII(78), LXXIX(79), LXXX(80),
        LXXXI(81), LXXXII(82), LXXXIII(83), LXXXIV(84), LXXXV(85), LXXXVI(86), LXXXVII(87), LXXXVIII(88), LXXXIX(89), XC(90),
        XCI(91), XCII(92), XCIII(93), XCIV(94), XCV(95), XCVI(96), XCVII(97), XCVIII(98), XCIX(99), C(100), CI(101);

        final String value;

        RomanNumeral(int value) {
            this.value = String.valueOf(value);
        }

        String getValue() {
            return value;
        }

        static Optional<RomanNumeral> valueOf(int value) {
            return Arrays.stream(values())
                    .filter(i -> i.ordinal() == value)
                    .findFirst();
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
    }

    static String calc(String input) {

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
                            // Get roman number
                            Optional<RomanNumeral> roman = RomanNumeral.valueOf(result-1);

                            // If it could be converted into a readable result -> do it,
                            // otherwise tell user they have messed something up
                            if (roman.isPresent()) {
                                return String.valueOf(roman.get());
                            } else {
                                throw new IllegalArgumentException("Out of bounds");
                            }
                        }
                    }
                }
                // It's a Arabic
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