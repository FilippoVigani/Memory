import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 * @author Filippo Vigani
 *
 */
public class WindowShell extends JFrame implements ActionListener {
	
	Page currentPage;
	
	public WindowShell() {
		setTitle("Simple example");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		currentPage = new MainPage();
		add(currentPage);
		
		loadNewPage(new CreateLobbyPage(currentPage));
		
		currentPage.previousPageButton.addActionListener(this);
	}
	
	private void loadPreviousPage(){
		/*
		getContentPane().removeAll();
		getContentPane().invalidate();
		getContentPane().add(currentPage.getSourcePage());
		getContentPane().revalidate();
		//repaint();
		*/
		if (!currentPage.isRoot()){
			setContentPane(currentPage.getSourcePage());
			pack();
		}
	}
	
	private void loadNewPage(Page page){
		setContentPane(page);
		pack();
		currentPage = page;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == currentPage.getPreviousPageButton()){
			loadPreviousPage();
		}
	}

}
