package fvsl.memory.client.ui;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.sql.Connection;
import java.util.*;

import javax.swing.JFrame;

import fvsl.memory.client.ui.PageListeners.*;
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
			pageManager.loadNewPage(new LobbyPageView((Lobby)e.getSource()));
		}
	});
	
	clw.getController().addEventListener(new GoToLobbyEventListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			pageManager.loadNewPage(new LobbyPageView((Lobby)e.getSource()));
		}
		
	});
}

}
