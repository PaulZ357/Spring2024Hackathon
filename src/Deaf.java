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
    static int answer = 0;

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
        
        JButton buttonSubmit = new JButton("Submit");// creating instance of JButton

        String text =   "Click on the dinosaurs so that your teammate can hear what they";
        String text2 =  "have to say, and determine which one of them is lying.";
        String text3 =  "Your teammate will tell you which dinosaur has lied.";
        JLabel label = new JLabel(text,JLabel.LEFT);
        JLabel label2 = new JLabel(text2,JLabel.LEFT);
        JLabel label3 = new JLabel(text3,JLabel.LEFT);
        label.setBounds(10, 0, 888, 64);
        label.setFont(new Font("Serif", Font.PLAIN, 32));
        label2.setBounds(10, 32, 888, 64);
        label2.setFont(new Font("Serif", Font.PLAIN, 32));
        label3.setBounds(10, 128, 888, 64);
        label3.setFont(new Font("Serif", Font.PLAIN, 32));

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
        ActionListener act4 = new ActionListener() { // SUBMITTING
            public void actionPerformed(ActionEvent e) {
                // Define what happens when the button is clicked
                if (answer == 1){
                    deaf.sendMessage(4);
                    JOptionPane.showMessageDialog(null, "That was the right answer!");
                }
                else if (answer == 2) {
                    deaf.sendMessage(5);
                    JOptionPane.showMessageDialog(null, "That was the wrong answer!");
                }
                else if (answer == 3){
                    deaf.sendMessage(6);
                    JOptionPane.showMessageDialog(null, "That was the wrong answer!");
                }
            }
        };

        
        ActionListener selectA = new ActionListener() { // SUBMITTING
            public void actionPerformed(ActionEvent e) {
                // Define what happens when the button is clicked
                answer = 1;
            }
        };
        ActionListener selectB = new ActionListener() { // SUBMITTING
            public void actionPerformed(ActionEvent e) {
                // Define what happens when the button is clicked
                answer = 2;
            }
        };
        ActionListener selectC = new ActionListener() { // SUBMITTING
            public void actionPerformed(ActionEvent e) {
                // Define what happens when the button is clicked
                answer = 3;
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
        frame.add(buttonSubmit);//adding button in JFrame
        frame.add(label);
        frame.add(label2);
        frame.add(label3);

        JRadioButton optionA = new JRadioButton("Green Dinosaur");
        JRadioButton optionB = new JRadioButton("Blue Dinosaur");
        JRadioButton optionC = new JRadioButton("Brown Dinosaur");
        ButtonGroup group = new ButtonGroup();
        group.add(optionA);
        optionA.addActionListener(selectA);
        group.add(optionB);
        optionB.addActionListener(selectB);
        group.add(optionC);
        optionC.addActionListener(selectC);

        buttonSubmit.setBounds(100, 640, 128, 32);// x axis, y axis, width, height
        buttonSubmit.addActionListener(act4);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(3, 1)); // here r could be 1 and c could be 2 
        radioPanel.add(optionA);
        radioPanel.add(optionB);
        radioPanel.add(optionC);

        frame.add(radioPanel);
        radioPanel.setBounds(64, 512, 200, 100);

        JLabel question = new JLabel("Which dinosaur lied?",JLabel.LEFT);
        question.setFont(new Font("Serif", Font.PLAIN, 24));
        question.setBounds(64,470,512,32);
        frame.add(question);

        frame.setVisible(true);//making the frame visible  
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