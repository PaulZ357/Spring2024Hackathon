import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

public class TCPHandler implements Runnable {

    // whether client or server
    private boolean blind = false;
    // TCP connection
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    private ServerSocket serverSocket;

    // Audio
    AudioInputStream audioInputStream;
    Clip clip;

    public TCPHandler(Socket socket) {
        this.socket = socket;
        try {
            out = new DataOutputStream(this.socket.getOutputStream());
            in = new DataInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.blind = true;
    }

    public int receiveMessage() {
        int received = 0;
        try {
            received = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return received;
    }

    // server starts off this one
    public TCPHandler(ServerSocket socket) {
        this.blind = false;
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

    public void sendHello() {
        try {
            out.writeInt(1);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // the thread spins off and does this
    @Override
    public void run() {
        if (blind) {
            while (true) {

            }
        }
    }
}
