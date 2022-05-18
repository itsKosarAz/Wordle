import java.io.*;
import java.net.*;
import java.util.*;

public class server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket( 3333);
        while (true) {
            Scanner s = new Scanner(new File("wordle-answers-alphabetical.txt"));
            ArrayList<String> list = new ArrayList<String>();
            while (s.hasNext()){
                list.add(s.next());
            }
            s.close();
            int index = (int)(Math.random() * list.size());
            String word = list.get(index);
            System.out.println(word);
            try {
                Computation t1 = new Computation(serverSocket, word);
                Computation t2 = new Computation(serverSocket, word);
                t1.player2 = t2;
                t2.player2 = t1;
                t1.start();
                t2.start();
                t1.join();
                t2.join();
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

class Computation extends Thread {
    ServerSocket s;
    String word;
    Socket socket;
    public Computation player2;

    public Computation(ServerSocket s, String word) {
        this.s = s;
        this.word = word;
    }
    

    public void run() {
            try {
                socket = s.accept();
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                String name = dis.readUTF();
                dos.writeUTF(word);
                String Result = dis.readUTF();
                if(Result.equals(name + " got it")){
                    System.out.println(Result);
                    dos.writeUTF(Result);
                    DataOutputStream dos2 = new DataOutputStream(player2.socket.getOutputStream());
                    dos2.writeUTF(Result);
                    socket.close();
                } else {
                    System.out.println(Result);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
}