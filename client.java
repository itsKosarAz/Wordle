//https://gist.github.com/cfreshman/a03ef2cba789d8cf00c08f767e0fad7b

import java.io.*;
import java.net.*;
import java.util.*;

public class client {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Wordle!");
        Scanner in = new Scanner(System.in);
        String input;
        boolean exist = false;
        boolean gotit = false;
        
        Dictionary dic = new Dictionary();
        Socket s = new Socket("127.0.0.1",3333);
        System.out.println("Enter your name");
        String name = in.nextLine();
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        DataInputStream dis = new DataInputStream(s.getInputStream());
        dos.writeUTF(name);
        String word = dis.readUTF();
        thread t = new thread(dis);
        t.start();
        for (int counter = 0; counter < 6; counter++) {
            do {
                System.out.println("Type a 5 letter word: ");
                input = in.nextLine();
                exist = dic.contains(input);
            } while (input.length() != 5 || exist == false);
    
    
            for (int i = 0; i < 5; i++) {
                if (input.charAt(i) == word.charAt(i)) {
                    System.out.print(ConsoleColors.GREEN + input.charAt(i) + ConsoleColors.RESET);
                } 
                else {
                    ColorPrint(i,word,input);
                }
                
            }
            System.out.println();
            if ((input.equals(word))) {
                System.out.println("Correct!");
                gotit = true;
                dos.writeUTF(name + " got it");
                break;
            }
    
        }
        if(!gotit) {
            dos.writeUTF(name + " failed");
            System.out.println("Sorry you didn't guess the word correctly, try again another time. \nThe correct word was: " + word);
        }
    }
    public static void ColorPrint(int i, String s, String input) {
        for (int j = 0; j < 5; j++) {
            if (input.charAt(i) ==s.charAt(j)) {
                System.out.print(ConsoleColors.YELLOW + input.charAt(i) + ConsoleColors.RESET);
                return;
            }
        }
        System.out.print(input.charAt(i));

    }
}

class ConsoleColors {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
}
class thread extends Thread {
    DataInputStream dis;

    public thread(DataInputStream dis) {
        this.dis = dis;
    }
    public void run() {
        try {
            String Result = dis.readUTF();
            System.out.println(Result);
        } catch (Exception e) {
            //TODO: handle exception
        }
        System.exit(0);
    }
}