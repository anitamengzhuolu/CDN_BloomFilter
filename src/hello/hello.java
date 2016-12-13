package hello;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;

public class hello
{
	String inputName = "";
	static bloomfilter bf = new bloomfilter(500000,10);
	
	private static void prepare_bf(){
		Scanner scanner;
		try {
			scanner = new Scanner(new File("data/CSV_Database_of_First_Names.txt"));
	        while(scanner.hasNext()){
	        	int hashed = scanner.next().hashCode();
	        	bf.add(hashed);
	            //System.out.print(scanner.next()+'/');
	        }
	        scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
    private void displayGUI()
    {
    	
        JFrame frame = new JFrame("Unique name checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setOpaque(true);
        contentPane.setBackground(new Color(230,255,230));
        contentPane.setLayout(null);


		BufferedImage title_pic;
		try {
			title_pic = ImageIO.read(new File("images/title_cdn.png"));
			JLabel picLabel = new JLabel(new ImageIcon(title_pic));
			picLabel.setSize(600, 100);
			picLabel.setLocation(5, 5);
			contentPane.add(picLabel);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("no such file");
		}
        
		BufferedImage mid_pic;
		try {
			mid_pic = ImageIO.read(new File("images/middle_cdn.png"));
			JLabel picLabel = new JLabel(new ImageIcon(mid_pic));
			picLabel.setSize(600, 350);
			picLabel.setLocation(5, 100);
			contentPane.add(picLabel);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("no such file");
		}
		
        JLabel label = new JLabel(
            "Please enter name you want to check uniqueness for a child or pet below:"
                                    , JLabel.CENTER);
        label.setSize(600, 100);
        label.setLocation(5, 50);
        label.setForeground(new Color(10,150,10));
        
        JButton button = new JButton("CHECK!");
        button.setSize(100, 30);
        button.setLocation(255, 170);
        
        JTextField name=new JTextField(10);
        name.setLocation(230, 120);
        name.setSize(150, 30);
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {     
            	inputName = name.getText();   
            	int hashed = inputName.hashCode();
            	if(bf.check(hashed)){
            		JOptionPane.showMessageDialog(null, "Not unique");
            	}else{
            		JOptionPane.showMessageDialog(null, "Unique");
            		bf.add(hashed);
            	}
            	//JOptionPane.showMessageDialog(null, hashed);
            }
         }); 

        contentPane.add(label);
        contentPane.add(name);
        contentPane.add(button);
        
        frame.setContentPane(contentPane);
        frame.setSize(600, 400);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String... args)
    {
    	prepare_bf();
    	
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new hello().displayGUI();
            }
        });
    }
}