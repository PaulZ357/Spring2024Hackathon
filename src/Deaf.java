import javax.swing.*;
public class Deaf {
    public static void main(String[] args) {
        JFrame frame = new JFrame();//creating instance of JFrame  
        JButton button = new JButton("click");//creating instance of JButton  
        button.setBounds(130,100,100, 40);//x axis, y axis, width, height  
                
        frame.add(button);//adding button in JFrame  
                
        frame.setSize(400,500);//400 width and 500 height  
        frame.setLayout(null);//using no layout managers  
        frame.setVisible(true);//making the frame visible  
    }
}