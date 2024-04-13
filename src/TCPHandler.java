import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPHandler implements Runnable
{

    // whether client or server
    private boolean client = false;
    //TCP connection
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    // server data
    private ServerSocket serverSocket;

    // spun off by client
    public TCPHandler(Socket socket)
    {
        this.client = true;
        this.socket = socket;
        try
        {
            out = new DataOutputStream(this.socket.getOutputStream());
            in = new DataInputStream(this.socket.getInputStream());
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    // server starts off this one
    public TCPHandler(ServerSocket socket){
        this.client = false;
        this.serverSocket = socket;
        // accept client, and update input/output streams
        try {
            System.out.println("Hello");
            Socket temp = serverSocket.accept();
            out = new DataOutputStream(temp.getOutputStream());
            in = new DataInputStream(temp.getInputStream());
            // TCPHandler ch = new TCPHandler(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendHello(){
        try {
            out.writeInt(1);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    // the thread spins off and does this
    @Override
    public void run()
    {
        // if client
        // if (client){
        //     while (true){

        //     }
        // }
        // // if server
        // else {
        //     while (true){

        //     }
        // }
    }
}
