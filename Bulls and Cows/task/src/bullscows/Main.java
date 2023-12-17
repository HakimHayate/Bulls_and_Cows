package bullscows;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        int[] lengths = gameInitiation();
        if (checkInputSymbols(lengths[0],lengths[2])) {
            // First check condition, if false the game starts.
            if (!checkError(lengths[2])) {
                printCode(lengths[0],lengths[1]);
                startGame(generateCode(lengths[0], lengths[1]));
                System.out.print("Congratulations! You guessed the secret code.\n");
            }
        }


    }


    // Check if the length of symbols is higher than 36 (which the maximum number of
    // symbols the program uses).
    public static boolean checkError(int len) {
        if (len>36) {
            System.out.printf("Error: can't generate a secret "
                    + "number with a length of %d because "
                    + "there aren't enough unique digits.\n", len);
        }
        return len>36;
    }

    // Start the game.
    public static void startGame(String randomNumber) {
        int round = 0;
        while(true) {
            round++;
            System.out.printf("Turn %d:\n",round);
            String guess = sc.next();
            if (play(randomNumber, guess)) break;
        }
    }


    // Initiate the game.
    public static int[] gameInitiation() {


        // Ask for the length of the code and check if it's a valid input.
        System.out.print("Input the length of the secret code:\n");
        int len_code = checkInvalidLengthCode();
        // Ask for the possible symbols in the code and check if it's a valid input.
        System.out.print("Input the number of possible symbols in the code:\n");
        int possible_symbols = checkInvalidSymbolsLength();

        // Get the number of alphabets that will be used to generate the code.
        int len_alphabet;
        if (possible_symbols > 10) {
            len_alphabet = possible_symbols - 11;
        } else len_alphabet = 0;


        // Return array containing the lengths.
        return new int[] {len_code, len_alphabet, possible_symbols} ;
    }


    // Method to check if the input for code length is valid.
    private static int checkInvalidLengthCode() {
        if (sc.hasNextInt()) {
            int len_code = sc.nextInt();
            return len_code;
        }
        else{
            // If input is not an integer, the program still want to
            // capture it, so it can show the user the error.
            String invalidInput = sc.next();
            System.out.printf("Error: `\"%s\" isn't a valid number.\n", invalidInput);
            return 0;
        }

    }


    // Method to check if the input for the length of symbols is valid.
    private static int checkInvalidSymbolsLength() {
        if (sc.hasNextInt()) {
            int len_symbols = sc.nextInt();
            return len_symbols;
        }
        else{
            String invalidInput = sc.next();
            System.out.printf("Error: `\"%s\" isn't a valid number.\n", invalidInput);
            return 0;
        }
    }


    // Check if program can generate a code with the possible number of symbols given.
    public static boolean checkInputSymbols(int len_code, int len_symbols) {
        if (len_symbols<10 || (len_code>10 && len_symbols<len_code)) {
            System.out.printf("Error: it's not possible " +
                    "to generate a code with a length of %d with %d unique symbols.\n",len_code, len_symbols);
        }
        return !(len_symbols<10 || (len_code>10 && len_symbols<len_code));
    }


    // Print the code is prepared.
    public static void printCode(int length_code, int length_symbols) {
        String alpabet = alphabet(length_symbols);
        StringBuilder etoiles = new StringBuilder();
        for (int i=0;i<length_code;i++) {
            etoiles.append("*");
        }
        if (length_symbols == 0) {
            System.out.printf("The secret is prepared: %s (0-9).", etoiles);
        }
        else {
            System.out.printf("The secret is prepared: %s (0-9, %c-%c).",
                    etoiles, alpabet.charAt(0), alpabet.charAt(length_symbols));
        }
    }


    // Calculate the number of bulls and cows for a single turn.
    public static boolean play(String secretNumber, String guess) {
        int cows = 0;
        int bulls = 0;
        for (int i=0;i<secretNumber.length();i++) {
            for (int j=0;j<guess.length();j++) {

                if(guess.charAt(j) == secretNumber.charAt(i)) {

                    if (i==j) {
                        bulls++;
                    }
                    else cows++;
                }
            }
        }
        System.out.printf("Grade: %d bull(s) and %d cow(s).\n", bulls, cows);
        return secretNumber.length() == bulls;

    }


    // Generate random code containing digits and symbols.
    public static String generateCode(int length_digits, int length_symbols) {
        String random_digits = generateRandom(length_digits);
        String random_symbols = generateSymbols(length_symbols);
        // Since I'll modify the string, I'll choose to convert it to
        // StringBuilder since it's mutable.
        StringBuilder code = new StringBuilder(random_digits);
        // Here I'll randomly combine the code with symbols.
        int[] permutationOfIndex = generatePermutationIndex(length_symbols);
        if (length_digits == 1 || length_symbols == 0) return code.toString();
        else {
            for (int i = 0; i < length_digits; i++) {
                if (generateRandomBoolean()) {
                    code.setCharAt(i, random_symbols.charAt(permutationOfIndex[i]));
                }
            }
            return code.toString();
        }
    }


    // Generate random boolean.
    public static boolean generateRandomBoolean() {
        Random r = new Random();
        return r.nextBoolean();
    }


    // Generate random index.
    public static int[] generatePermutationIndex(int length) {
        Random r = new Random();
        int[] index = new int[length];

        for (int i=0;i<length;i++) {
            index[i] = i;
        }

        // Permutate the array
        for (int i=length-1;i>0;i--) {
            int j = r.nextInt(i+1);
            int temp = index[i];
            index[i] = index[j];
            index[j] = temp;
        }
        return index;
    }


    // Generate symbols from a-z given the possible length of symbols.
    public static String generateSymbols(int length) {
        String alphabet = alphabet(length);
        StringBuilder symbols = new StringBuilder();
        for (int i=0;i<=length;i++) {
            symbols.append(alphabet.charAt(i));
        }
        return symbols.toString();
    }


    // Return alphabet.
    private static String alphabet(int len) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        return alphabet;
    }


    // Generate random series of digits given length.
    public static String generateRandom(int input) {
        Random r = new Random();
        StringBuilder randomNumber = new StringBuilder();
        String pseudoRandomNumber = Integer.toString(r.nextInt());
        int counter = pseudoRandomNumber.length()-1;

        // As long as the number of digits in the random number is smaller than input we keep iterating.
        while (input>randomNumber.length()) {
            // Create a pseudoRandomNumber once we reach max number.
            if (counter < 0) {
                pseudoRandomNumber = generatepseudoRandom();
                counter = pseudoRandomNumber.length()-1;
            }
            // Check if we add the digit or no.
            if (add(pseudoRandomNumber, randomNumber, counter, input)) {
                randomNumber.append(pseudoRandomNumber.charAt(counter));
            }
            counter--;

        }
        return randomNumber.toString();
    }

    // Not used anymore since the program use the Random class which gives better
    // random numbers.
    public static String generatepseudoRandom() {
        long pseudoRandomNumber = System.nanoTime();
        String pseudoR = Long.toString(pseudoRandomNumber);
        return pseudoR;
    }

    // Add one random number to a StringBuilder given.
    public static boolean add(String pseudoRandomNumber, StringBuilder randomNumber, int counter, int input) {
        boolean add;

        add = !isPresent(randomNumber, pseudoRandomNumber, counter);

        return add;
    }


    // Return true if a digit is not unique.
    public static boolean isPresent(StringBuilder randomNumber, String pseudoRandomNumber, int counter) {
        boolean isPresent = false;
        for (int i=0;i<randomNumber.length();i++) {

            if (randomNumber.charAt(i) == pseudoRandomNumber.charAt(counter)) {
                isPresent = true;
                break;
            }
        }
        return isPresent;

    }

    private static Scanner sc = new Scanner(System.in);
}



