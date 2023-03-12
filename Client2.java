import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("127.0.0.1",9998);

        Scanner scanner=new Scanner(System.in);

        DataInputStream in=new DataInputStream(socket.getInputStream());
        DataOutputStream out=new DataOutputStream(socket.getOutputStream());

        out.writeUTF("c2");

        while (true){
            System.out.print("Your text: ");
            String msg=scanner.nextLine();
            out.writeUTF(msg);
            System.out.println("Text length: "+in.readUTF());
        }
    }
}