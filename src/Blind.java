import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Blind {
    // TCP
    private Socket socket;
    private TCPHandler tcpHandler;

    public Blind(Socket socket) {
        this.socket = socket;

        tcpHandler = new TCPHandler(socket);
    }

    public int received() {
        int message = tcpHandler.receiveMessage();
        return message;
    }

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("10.111.109.240", 5000);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Blind blindChar = new Blind(socket);

        // System.out.println(blindChar.received());
    }
}
