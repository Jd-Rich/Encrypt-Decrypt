import javax.annotation.processing.Filer;
import java.util.Scanner;
import java.io.*;

public class Main {
    private static String encrypt(String str, int shift){
        String newStr = "";
        for(int i = 0; i < str.length(); i++) {
            newStr += (char) (str.charAt(i) + shift);
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

    private static void encryptToFile(File file, int shift) {

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
                    fileIn = args[i + 1]; //file to read from
                    break;
                case "-out":
                    fileOut = args[i + 1]; //file to write too
                    break;
            }
        }
        //test if mode is left blank or not
        if(mode.isBlank()) {
            mode = "enc";
        }

        if(mode.equals("enc")) {
            System.out.println(encrypt(data, key));
        }
        else if(mode.equals("dec")) {
            System.out.println(decrypt(data, key));
        }

        int shift = 5;
        System.out.println(encryptFromFile("data.txt", shift));
    }
}


