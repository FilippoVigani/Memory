package fvsl.memory.client.ui;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import fvsl.memory.client.ui.MainPageController.GoToCreateLobbyEvent;
import fvsl.memory.client.ui.MainPageController.GoToCreateLobbyEventListener;

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
		
		init();
	}

	private void init(){
		MainPageView mpw = new MainPageView();
		
		pageManager = new PageManager(this, mpw); 
		
		mpw.getController().addEventListener(new GoToCreateLobbyEventListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				pageManager.loadNewPage(new CreateLobbyPageView());
			}
		});
	}
	
}
