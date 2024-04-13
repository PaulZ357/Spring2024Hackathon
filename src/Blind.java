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

    public void sendACK() {
        tcpHandler.sendACK();
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

        JFrame frame = new JFrame("test");// creating instance of JFrame
        JButton button = new JButton();// creating instance of JButton
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // maximizes window
        frame.setSize(400, 500);// 400 width and 500 height
        frame.setLayout(null);// using no layout managers
        ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Define what happens when the button is clicked
                blindChar.sendACK();
                // System.out.println("In here");

            }
        };
        button.setBounds(600, 256, 128, 128);// x axis, y axis, width, height
        button.addActionListener(action);
        JLabel instructions =  new JLabel("Listen to the statements by the dinosaurs and tell your teammate", JLabel.LEFT);
        JLabel instructions2 =  new JLabel("which one is lying by clicking the button.", JLabel.LEFT);
        instructions.setBounds(10, 0, 888, 40);
        instructions2.setBounds(10, 32, 888, 40);
        instructions.setFont(new Font("Serif", Font.PLAIN, 32));
        instructions2.setFont(new Font("Serif", Font.PLAIN, 32));
        frame.add(button);
        frame.add(instructions);
        frame.add(instructions2);

        frame.setVisible(true);// making the frame visible

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(blindChar.tcpHandler);
        // System.out.println(blindChar.received());
    }
}
