package fvsl.memory.client.ui;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 * @author Filippo Vigani
 *
 */
public class WindowShell extends JFrame {
	
	Page currentPage;
	
	public WindowShell() {
		setTitle("Simple example");
		setSize(1200, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		currentPage = new MainPage(this);
		add(currentPage);
		
		currentPage.loadNewPage(new CreateLobbyPage(this));
	}

}
