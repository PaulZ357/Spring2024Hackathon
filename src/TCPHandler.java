import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPHandler implements Runnable
{
    //TCP connection
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    public TCPHandler(Socket socket)
    {
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

    @Override
    public void run()
    {

    }
}
