import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Deaf {
    // TCP
    private ServerSocket socket;
    private TCPHandler tcpHandler;

    public static boolean buzzReceived = false;

    public Deaf(ServerSocket socket) {
        this.socket = socket;

        tcpHandler = new TCPHandler(socket);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(tcpHandler);

    }

    public void sendMessage(int message) {
        tcpHandler.sendMessage(message);
    }

    public boolean getBuzzReceived(TCPHandler handler) {
        return handler.getBuzzReceived();
    }

    public TCPHandler getTcpHandler(){
        return this.tcpHandler;
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

        JFrame frame = new JFrame("test");// creating instance of JFrame
        Icon iconA = new ImageIcon("res/A.png");
        Icon iconB = new ImageIcon("res/B.png");
        Icon iconC = new ImageIcon("res/C.png");
        JButton button = new JButton(iconA);// creating instance of JButton
        JButton button2 = new JButton(iconB);// creating instance of JButton
        JButton button3 = new JButton(iconC);// creating instance of JButton

        String text = "Click on the dinosaurs so that your teammate"+"can hear what they have to say, and determine which one of them is lying.";
        //JLabel label = new JLabel(text,JLabel.LEFT);
        JLabel label = new JLabel();
        JTextArea textarea = new JTextArea(text);
        label.setBounds(0, 0, 840, 256);
        //label.setFont(new Font("Serif", Font.PLAIN, 32));
        label.add(textarea);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // maximizes window
        frame.setSize(400, 500);// 400 width and 500 height
        frame.setLayout(null);// using no layout managers

        ActionListener act1 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Define what happens when the button is clicked
                deaf.sendMessage(1);
                JOptionPane.showMessageDialog(null, "Talked to Green Dinosaur");

            }
        };
        ActionListener act2 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Define what happens when the button is clicked
                deaf.sendMessage(2);
                JOptionPane.showMessageDialog(null, "Talked to Blue Dinosaur");

            }
        };
        ActionListener act3 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Define what happens when the button is clicked
                deaf.sendMessage(3);
                JOptionPane.showMessageDialog(null, "Talked to Brown Dinosaur");

            }
        };

        button.setBounds(300, 256, 128, 128);// x axis, y axis, width, height
        button.addActionListener(act1);

        button2.setBounds(600, 256, 128, 128);// x axis, y axis, width, height
        button2.addActionListener(act2);

        button3.setBounds(900, 256, 128, 128);// x axis, y axis, width, height
        button3.addActionListener(act3);

        frame.add(button);//adding button in JFrame
        frame.add(button2);//adding button in JFrame
        frame.add(button3);//adding button in JFrame
        frame.add(label);

        frame.setVisible(true);//making the frame visible  

        JRadioButton optionA = new JRadioButton();
        JRadioButton optionB = new JRadioButton();
        JRadioButton optionC = new JRadioButton();
        ButtonGroup group = new ButtonGroup();
        group.add(optionA);
        group.add(optionB);
        group.add(optionC);

        // use the swing worker to check for updates from TCPHandler
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                while (true) {
                    
                    if (SharedData.DataHolder.sharedBoolean){
                        showBuzzAnimation(frame);
                        SharedData.DataHolder.sharedBoolean = false;
                    }
                }
            }

        };
        worker.execute();
    }

    public static void showBuzzAnimation(JFrame frame) {

        JPanel greenPanel = new JPanel();
		greenPanel.setBackground(Color.green);
		greenPanel.setBounds(600, 500, 200,200);
		greenPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel();
		label.setText("Your partner pressed the button!");
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
        greenPanel.add(label);

        frame.add(greenPanel);
        frame.revalidate();
        frame.repaint();
		// label.setBounds(100, 100, 75, 75);

        // JPanel panel = new JPanel() {
        //     @Override
        //     protected void paintComponent(Graphics g) {
        //         super.paintComponent(g);
        //         g.setColor(Color.GREEN);
        //         int diameter = 200; // Adjust size of circle
        //         int x = (getWidth() - diameter) / 2;
        //         int y = (getHeight() - diameter) / 2;
        //         g.fillOval(x, y, diameter, diameter);
        //     }
        // };
        // panel.setBounds(1000, 1000, 50, 50); // Set bounds for the panel
        // System.out.println("Animation shown");

        // frame.add(panel);
        // frame.revalidate();
        // frame.repaint();

        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(greenPanel);
                frame.revalidate();
                frame.repaint();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}