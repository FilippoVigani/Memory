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

import fvsl.memory.client.ui.MainPageController.GoToCreateLobbyEvent;
import fvsl.memory.client.ui.MainPageController.GoToCreateLobbyEventListener;
import fvsl.memory.client.ui.Request.RequestAction;
import fvsl.memory.client.ui.Request.RequestType;

/**
 * @author Filippo Vigani
 *
 */
public class WindowShell extends JFrame {

	PageManager pageManager;

	private static InetAddress host;
	private static final int PORT = 17829;

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
