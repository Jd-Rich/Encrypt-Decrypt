import javax.annotation.processing.Filer;
import java.util.Scanner;
import java.io.*;

public class Main {
    private static String encrypt(String str, int shift){
        String newStr = "";
        for(int i = 0; i < str.length(); i++) {
            //newStr += (char) (str.charAt(i) + shift); //unicode encryption
            //shift alg 'A' - 'Z' && 'a' - 'z'
            if (Character.isLetter((str.charAt(i))) || str.charAt(i) == ' ') {
                char c = (char) (str.charAt(i) + shift);
                if (c > 'z') {
                    newStr += (char) (str.charAt(i) - (26 - shift));
                } else if (str.charAt(i) == ' ') {
                    newStr += str.charAt(i);
                } else {
                    newStr += (char) (str.charAt(i) + shift);
                }
            }

        }
        return newStr;
    }

    private static String unicodeEncrypt(String str, int shift) {
        // current char + key value - 26
        String newStr = "";
        for(int i = 0; i < str.length(); i++) {
            if (Character.isLetter((str.charAt(i))) || str.charAt(i) == ' ') {
                char c = (char) (str.charAt(i) + shift);
                if (c > 'z') {
                    newStr += (char) (str.charAt(i) - (26 - shift));
                } else if (str.charAt(i) == ' ') {
                    newStr += str.charAt(i);
                } else {
                    newStr += (char) (str.charAt(i) + shift);
                }
            }

        }
        return newStr;
    }

    private static String decrypt(String str, int shift) {
        String newStr = "";
        for(int i = 0; i < str.length(); i++) {
            newStr += (char) (str.charAt(i) - shift);
        }
        return newStr;
    }

    private static String getStringFromFile(String FileIn) {
        String stringFromFile = "";
        int ch;
        try {
            BufferedReader buffer = new BufferedReader((new FileReader(FileIn)));
            while((ch = buffer.read()) != -1) {
                stringFromFile += (char)ch;
            }
            buffer.close();
        } catch(FileNotFoundException x) {
            System.out.println("File not found");
        } catch(IOException x) {
            System.out.println("IO expection.");
        }
        return stringFromFile;
    }

    private static void decryptFromFile(String fileIn, int shift) {   //previously String
        String stringFromFile = "";
        String decryptedStringFromFile = "";
        int ch;
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(fileIn));
            while((ch = buffer.read()) != -1) {
                stringFromFile += (char)ch;
            }
            buffer.close();
        }
        catch(FileNotFoundException x) {
            System.out.println("decryptFromFile Error filenotfound " + fileIn);
        }
        catch(IOException x) {
            System.out.println("decryptFromFile Error ioexception");
        }
        decryptedStringFromFile = decrypt(stringFromFile, shift);
        System.out.println(decryptedStringFromFile);
    }

    private static void decryptToFile(String fileOut, String str, int shift) {
        String decryptedStr = decrypt(str, shift);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileOut));
            writer.write(decryptedStr);
            writer.close();
        } catch(IOException x) {
            System.out.println("decryptToFile Error");
        }
    }

    private static void decryptToFileFromFile(String fileOut, String fileIn, int shift) {
        String decryptedStr = decrypt(fileIn, shift);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileOut));
            writer.write(decryptedStr);
            writer.close();
        } catch(IOException x) {
            System.out.println("decryptToFile Error");
        }
    }

    private static String encryptFromFile(String file, int shift) { //gets message from a file and encrypts it
        String stringFromFile = " ";
        String encryptedStringFromFile = " ";
        int ch;
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            while((ch = buffer.read()) != -1) {
                stringFromFile += (char)ch;
            }
        }
        catch(FileNotFoundException x) {
            System.out.println("Error");
        }
        catch(IOException x) {
            System.out.println("Error");
        }
        encryptedStringFromFile = encrypt(stringFromFile, shift);
        return encryptedStringFromFile;
    }

    private static void encryptToFile(String fileOut, String str, int shift) { //taking encrypted string and sending to a file
        String crypticStr = encrypt(str, shift);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileOut));
            writer.write(crypticStr);
            writer.close();
        } catch(IOException x) {
            System.out.println("encryptToFile Error");
        }
    }

    private static void encryptToFile(String fileOut, String str) { //taking encrypted string and sending to a file, used if string is passed already encrypted
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileOut));
            writer.write(str);
            writer.close();
        } catch(IOException x) {
            System.out.println("encryptToFile 2 arg Error");
        }
    }

    public static void main(String[] args) {
        int key = 0; //initialize to 0 satisfies default 0 if not specified.
        String mode = " ";
        String data = " ";
        String fileIn = " ";
        String fileOut = " ";
        Scanner input = new Scanner(System.in);

        for(int i =0; i < args.length; i+=2) {
            switch(args[i]) {
                case "-mode":
                    mode = args[i + 1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    data = args[i + 1];
                    break;
                case "-in":
                    fileIn = args[i + 1];
                    break;
                case "-out":
                    fileOut = args[i + 1];
                    break;
            }
        }
        //test if mode is left blank or not
        if(mode.isBlank()) {
            mode = "enc";
        }

        if (!fileIn.isEmpty() && !fileOut.isEmpty() && mode.equals("enc")) { //-in && -out w/ enc
            encryptToFile(fileOut, getStringFromFile(fileIn), key);
        } else if (!fileIn.isEmpty() && !fileOut.isEmpty() && mode.equals("dec")) { //-in && -out w/ dec
            decryptToFileFromFile(fileOut, getStringFromFile(fileIn), key);
        } else if (!fileIn.isEmpty() && fileOut.isEmpty() && !data.isEmpty()) { // -in with -data and enc by default -- rejects file accepts from data
            System.out.println(encrypt(data, key));
        } else if (fileIn.isEmpty() && !fileOut.isEmpty() && mode.equals("enc")) { //-out w/ enc
            encryptToFile(fileOut, data, key);
        } else if (fileIn.isEmpty() && !fileOut.isEmpty() && mode.equals("dec")) { //-out w/ dec
            decryptToFile(fileOut, data, key);
        } else if (!fileIn.isEmpty() && fileOut.isEmpty() && mode.equals("enc")) { // -in w/ enc
            encryptFromFile(fileIn, key);
        } else if(!fileIn.isEmpty() && fileOut.isEmpty() && mode.equals("dec")) {
            decryptFromFile(fileIn, key);
        }else if(mode.equals("enc")) {
            System.out.println(encrypt(data, key));
        }
        else if(mode.equals("dec")) {
            System.out.println(decrypt(data, key));
        }
    }
}


