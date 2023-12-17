import java.util.Scanner;

class EvenUpperCase {

    public static String upperEvenLetters(String message) {
        // write your code here
        String upperCase = message.toUpperCase();

        StringBuilder stringBuilder = new StringBuilder(message);

        for (int i=0;i<message.length();i++) {
            if (i%2==0){
                char c = upperCase.charAt(i);
                stringBuilder.setCharAt(i, c);
            }
        }
        return stringBuilder.toString();
    }

    // Don't change the code below
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String message = scanner.next();

        System.out.println(upperEvenLetters(message));
    }
}