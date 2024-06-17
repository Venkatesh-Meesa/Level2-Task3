import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ency {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("File Encryption/Decryption");

        // Prompt user to choose between encryption or decryption
        System.out.print("Enter 'E' for encryption or 'D' for decryption: ");
        char choice = scanner.nextLine().toUpperCase().charAt(0);

        if (choice == 'E') {
            encryptFile(scanner);
        } else if (choice == 'D') {
            decryptFile(scanner);
        } else {
            System.out.println("Invalid choice. Please enter 'E' or 'D'.");
        }

        scanner.close();
    }

    private static void encryptFile(Scanner scanner) {
        try {
            // Prompt user for file name or path
            System.out.print("Enter the path of the file to encrypt: ");
            String filePath = scanner.nextLine();

            // Read the content of the file
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }

            // Encrypt the content using a simple Caesar Cipher (shift by 3 positions)
            String encryptedContent = encrypt(content.toString(), 3);

            // Write the encrypted content to a new file
            String encryptedFilePath = filePath + ".encrypted";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(encryptedFilePath))) {
                writer.write(encryptedContent);
            }

            System.out.println("File encrypted successfully. Encrypted file saved as: " + encryptedFilePath);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void decryptFile(Scanner scanner) {
        try {
            // Prompt user for file name or path
            System.out.print("Enter the path of the file to decrypt: ");
            String filePath = scanner.nextLine();

            // Read the content of the encrypted file
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }

            // Decrypt the content using a simple Caesar Cipher (shift by -3 positions)
            String decryptedContent = decrypt(content.toString(), 3);

            // Write the decrypted content to a new file
            String decryptedFilePath = filePath.replace(".encrypted", "_decrypted.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(decryptedFilePath))) {
                writer.write(decryptedContent);
            }

            System.out.println("File decrypted successfully. Decrypted file saved as: " + decryptedFilePath);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (Character.isLetter(ch)) {
                char shifted = (char) (ch + shift);
                if ((Character.isUpperCase(ch) && shifted > 'Z') || (Character.isLowerCase(ch) && shifted > 'z')) {
                    shifted = (char) (ch - (26 - shift));
                }
                result.append(shifted);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    private static String decrypt(String text, int shift) {
        return encrypt(text, -shift); // Decryption is just the inverse of encryption
    }
}
