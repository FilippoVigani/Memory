package fvsl.memory.client.ui;
import javax.swing.JFrame;

/**
 * @author Filippo Vigani
 *
 */
public class WindowShell extends JFrame {
	
	PageManager pageManager;
	
	public WindowShell() {
		setTitle("Simple example");
		setSize(1200, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		pageManager = new PageManager(this, new MainPageView()); 
	}

}
