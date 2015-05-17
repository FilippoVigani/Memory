package fvsl.memory.client.shell;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import fvsl.memory.client.pages.PageListeners.GoToMainPageEventListener;
import fvsl.memory.client.pages.PageManager;
import fvsl.memory.client.pages.PageListeners.*;
import fvsl.memory.client.pages.createlobby.CreateLobbyPageView;
import fvsl.memory.client.pages.lobby.LobbyPageView;
import fvsl.memory.client.pages.main.MainPageView;
import fvsl.memory.client.sockets.GUIUpdaterRunnable;
import fvsl.memory.common.entities.Lobby;
/**
 * @author Filippo Vigani
 *
 */
public class WindowShell extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1980870011702344758L;
	private PageManager pageManager;
	
	MainPageView mpw;
	CreateLobbyPageView clw;

	public WindowShell() {
		setTitle("Simple example");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		init();
	}

private void init(){
	mpw = new MainPageView();
	clw = new CreateLobbyPageView();
	
	Application.setUpdater(new GUIUpdaterRunnable(mpw));
	Application.setUpdaterThread(new Thread(Application.getUpdater()));
	
	pageManager = new PageManager(this, mpw); 
	
	Application.getUpdaterThread().start();

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
	
	this.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
	    	if (Application.getUpdater() != null){
	    		Application.getUpdater().close(true);
	    	}
	        System.exit(1);
	    }
	});
}

}
