import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static HashMap<String,Socket> clients;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(9999);

        clients=new HashMap<>();

        ExecutorService executorService= Executors.newFixedThreadPool(5);
        while (true){
            Socket clint = serverSocket.accept();

            DataInputStream in=new DataInputStream(clint.getInputStream());
            String name=in.readUTF();
            clients.put(name,clint);

            executorService.execute(new ClientThread(name,clint,clients));
            System.out.println(name+" connected to server...");
        }
    }
}

class ClientThread extends Thread{
    private String name;
    private Socket client;
    private HashMap<String,Socket> clients;

    public ClientThread(String name, Socket client, HashMap<String, Socket> clients) {
        this.name = name;
        this.client = client;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {

            DataInputStream in=new DataInputStream(client.getInputStream());
            DataOutputStream out=new DataOutputStream(client.getOutputStream());

            while (true){

                String res=in.readUTF();
                System.out.println("Server Received: "+res);
                String[] data=res.split(">>");
                if(clients.containsKey(data[0])){
                    new DataOutputStream(clients.get(data[0]).getOutputStream()).writeUTF(name+" sends: "+data[1]);
                    System.out.println("Message("+data[1]+") sends to "+data[0]+"....");
                }else{
                    System.out.println(data[0]+" not found....");
                }

            }

        }catch (IOException e){
            System.out.println(e);
        }

    }
}