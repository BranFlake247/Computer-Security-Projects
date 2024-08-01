import java.util.Scanner;

public class SimpleSubstitutionCipher {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter a message to encrypt: ");
        String message = scan.nextLine();

        System.out.print("Enter the shift value for the message: ");
        int shift = scan.nextInt ();

        String encryptedMessage = encrypt(message, shift);

        System.out.println("Encrypted message:" + encryptedMessage + "\n");

    }

    public static String encrypt(String message, int shift) {
        
        StringBuilder encryptedMessage = new StringBuilder();

        message = message.toLowerCase();

        for (int i = 0; i < message.length(); i++){

            char c = message.charAt(i);

            if (Character.isLetter(c))
            {
                c = (char) ((c - 'a' + shift + 26) % 26 + 'a');
            }

            encryptedMessage.append(c);
        }

        return encryptedMessage.toString();
    }
}
