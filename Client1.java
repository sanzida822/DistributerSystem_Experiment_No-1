import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("127.0.0.1",9999);

        Scanner scanner=new Scanner(System.in);

        DataInputStream in=new DataInputStream(socket.getInputStream());
        DataOutputStream out=new DataOutputStream(socket.getOutputStream());

        Thread receivedThread=new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    try {
                        String msg=in.readUTF();
                        System.out.println(msg);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }

            }
        });

        out.writeUTF("c1");

        receivedThread.start();

        while (true){
            String msg=scanner.nextLine();
            out.writeUTF(msg);
        }
    }
}
