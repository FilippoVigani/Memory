package fvsl.memory.client.shell;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import fvsl.memory.client.entities.Lobby;
import fvsl.memory.client.pages.PageListeners;
import fvsl.memory.client.pages.PageListeners.GoToMainPageEventListener;
import fvsl.memory.client.pages.PageManager;
import fvsl.memory.client.pages.PageListeners.*;
import fvsl.memory.client.pages.createlobby.CreateLobbyPageView;
import fvsl.memory.client.pages.lobby.LobbyPageView;
import fvsl.memory.client.pages.main.MainPageView;
/**
 * @author Filippo Vigani
 *
 */
public class WindowShell extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1980870011702344758L;
	PageManager pageManager;

	public WindowShell() {
		setTitle("Simple example");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		init();
	}

private void init(){
	final MainPageView mpw = new MainPageView();
	final CreateLobbyPageView clw = new CreateLobbyPageView();
	
	pageManager = new PageManager(this, mpw); 

	mpw.getController().addEventListener(new GoToCreateLobbyEventListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			pageManager.loadNewPage(clw);
		}
	});
	
	mpw.getController().addEventListener(new GoToLobbyEventListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			LobbyPageView lpw = new LobbyPageView((Lobby)e.getSource());	
			pageManager.loadNewPage(lpw);
			lpw.getController().addEventListener(new GoToMainPageEventListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					pageManager.loadNewPage(mpw);
				}
			});
			
		}
	});
	
	clw.getController().addEventListener(new GoToLobbyEventListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			LobbyPageView lpw = new LobbyPageView((Lobby)e.getSource());
			pageManager.loadNewPage(lpw);
			lpw.getController().addEventListener(new GoToMainPageEventListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					pageManager.loadNewPage(mpw);
				}
			});
		}
		
		
	});
	
	clw.getController().addEventListener(new GoToMainPageEventListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			pageManager.loadNewPage(mpw);
		}
	});
	

}

}
