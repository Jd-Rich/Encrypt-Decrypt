import java.util.Scanner;
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

    public static void main(String[] args) {
        int key = 0;
        String mode = " ";
        String data = " ";
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
                    data = args[i+1];
                    break;
            }
        }

        //test if mode is left blank or not
        if(mode.isBlank()) {
            mode = "enc";
        }

        //test if -key or -data are blank
        if(data.isBlank()) {
            data = input.nextLine();
            key = input.nextInt();
        }

        if(mode.equals("enc")) {
            System.out.println(encrypt(data, key));
        }
        else if(mode.equals("dec")) {
            System.out.println(decrypt(data, key));
        }
    }
}


