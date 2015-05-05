package fvsl.memory.client.ui;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 * @author Filippo Vigani
 *
 */
public class WindowShell extends JFrame {
	
	PageManager pageManager;
	
	public WindowShell() {
		setTitle("Simple example");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		Dimension dimSchermo = Toolkit.getDefaultToolkit().getScreenSize();
		 int x = dimSchermo.width; 
	     int y = dimSchermo.height;
	     
		pageManager = new PageManager(this, new MainPage());
		setBounds(0,0,x,y);
	}

}
