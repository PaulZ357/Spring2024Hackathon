import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;  

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

        JFrame frame = new JFrame("test");//creating instance of JFrame  
        JButton button = new JButton();//creating instance of JButton
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // maximizes window
        frame.setSize(400,500);//400 width and 500 height  
        frame.setLayout(null);//using no layout managers 
        
        button.setBounds(600,256,128, 128);//x axis, y axis, width, height 
        frame.add(button);
        
        frame.setVisible(true);//making the frame visible  

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
