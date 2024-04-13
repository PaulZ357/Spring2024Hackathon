import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Deaf
{
    //TCP
    private Socket socket;
    private TCPHandler tcpHandler;

    public Deaf(Socket socket)
    {
        this.socket = socket;

        tcpHandler = new TCPHandler(socket);
    }
}
