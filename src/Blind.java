import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Executable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        JFrame frame = new JFrame("test");// creating instance of JFrame
        JButton button = new JButton();// creating instance of JButton
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // maximizes window
        frame.setSize(400, 500);// 400 width and 500 height
        frame.setLayout(null);// using no layout managers
        ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Define what happens when the button is clicked

            }
        };
        button.setBounds(600, 256, 128, 128);// x axis, y axis, width, height
        button.addActionListener(action);
        frame.add(button);

        frame.setVisible(true);// making the frame visible

        Socket socket = null;
        try {
            socket = new Socket("10.111.156.220", 5000);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Blind blindChar = new Blind(socket);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(blindChar.tcpHandler);
        // System.out.println(blindChar.received());
    }
}
