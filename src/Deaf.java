import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;  

public class Deaf
{
    //TCP
    private ServerSocket socket;
    private TCPHandler tcpHandler;


    public Deaf(ServerSocket socket)
    {
        this.socket = socket;

        tcpHandler = new TCPHandler(socket);
        
    }

    public void sendMessage()
    {
        tcpHandler.sendHello();
    }
    public static void main(String[] args) {
        Deaf deaf;
        ServerSocket temp = null;

        try {
            temp = new ServerSocket(5000);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        deaf = new Deaf(temp);


        JFrame frame = new JFrame("test");//creating instance of JFrame  
        Icon iconA = new ImageIcon("res/A.png");
        Icon iconB = new ImageIcon("res/B.png");
        Icon iconC = new ImageIcon("res/C.png");
        JButton button = new JButton(iconA);//creating instance of JButton  
        JButton button2 = new JButton(iconB);//creating instance of JButton
        JButton button3 = new JButton(iconC);//creating instance of JButton
        
        JLabel label = new JLabel();
        label.setText("test");
        label.setBounds(0, 0, 0, 0);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // maximizes window
        frame.setSize(400,500);//400 width and 500 height  
        frame.setLayout(null);//using no layout managers 

        ActionListener act1 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Define what happens when the button is clicked
                JOptionPane.showMessageDialog(null, "Button1 clicked!");
                deaf.sendMessage();
                
            }
        };
        ActionListener act2 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Define what happens when the button is clicked
                JOptionPane.showMessageDialog(null, "Button2 clicked!");
            }
        };
        ActionListener act3 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Define what happens when the button is clicked
                JOptionPane.showMessageDialog(null, "Button3 clicked!");
            }
        };

        button.setBounds(300,256,128, 128);//x axis, y axis, width, height 
        button.addActionListener(act1);
        
        button2.setBounds(600,256,128, 128);//x axis, y axis, width, height 
        button2.addActionListener(act2);
        
        button3.setBounds(900,256,128, 128);//x axis, y axis, width, height 
        button3.addActionListener(act3);

        frame.add(button);//adding button in JFrame
        frame.add(button2);//adding button in JFrame
        frame.add(button3);//adding button in JFrame
        
        frame.setVisible(true);//making the frame visible  
    }
}