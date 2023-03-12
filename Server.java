import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(9998);

        ExecutorService executorService= Executors.newFixedThreadPool(5);
        while (true){
            Socket clint = serverSocket.accept();

            DataInputStream in=new DataInputStream(clint.getInputStream());
            String name=in.readUTF();

            executorService.execute(new ClientThread(name,clint));
            System.out.println(name+" connected to server...");
        }
    }
}

class ClientThread extends Thread{
    private String name;
    private Socket client;

    public ClientThread(String name, Socket client) {
        this.name = name;
        this.client = client;
    }

    @Override
    public void run() {
        try {

            DataInputStream in=new DataInputStream(client.getInputStream());
            DataOutputStream out=new DataOutputStream(client.getOutputStream());

            while (true){
                String res=in.readUTF();
                System.out.println("Server Received: "+res);
                out.writeUTF(String.valueOf(res.length()));
            }

        }catch (IOException e){
            System.out.println(e);
        }

    }
}