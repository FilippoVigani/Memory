package fvsl.memory.client.shell;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import fvsl.memory.client.pages.PageListeners.GoToMainPageEventListener;
import fvsl.memory.client.pages.PageManager;
import fvsl.memory.client.pages.PageListeners.*;
import fvsl.memory.client.pages.createlobby.CreateLobbyPageView;
import fvsl.memory.client.pages.game.GamePageView;
import fvsl.memory.client.pages.lobby.LobbyPageView;
import fvsl.memory.client.pages.main.MainPageView;
import fvsl.memory.client.pages.scoreboard.ScoreboardPageView;
import fvsl.memory.client.sockets.GUIUpdaterRunnable;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.util.StringResources;

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
		setTitle(StringResources.windowTitle.toString());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		init();
		setLocationRelativeTo(null);
		// setResizable(false);
	}

	private void init() {
		mpw = new MainPageView();
		clw = new CreateLobbyPageView();

		Application.setUpdater(new GUIUpdaterRunnable(mpw));
		Application.setUpdaterThread(new Thread(Application.getUpdater()));

		pageManager = new PageManager(this, mpw);

		Application.getUpdaterThread().start();

		mpw.getController().addEventListener(new GoToCreateLobbyEventListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pageManager.loadNewPage(clw);
			}
		});

		mpw.getController().addEventListener(new GoToLobbyEventListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LobbyPageView lpw = new LobbyPageView((Lobby) e.getSource());
				pageManager.loadNewPage(lpw);
				lpw.getController().addEventListener(new GoToMainPageEventListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						pageManager.loadNewPage(mpw);
					}
				});

				final GamePageView gpw = new GamePageView((Lobby) e.getSource());

				gpw.getController().addEventListener(new GoToScoreboardEventListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						ScoreboardPageView scw = new ScoreboardPageView((Lobby) e.getSource());
						pageManager.loadNewPage(scw);
				scw.getController().addEventListener(new GoToMainPageEventListener(){
							@Override
							public void actionPerformed(ActionEvent e) {
							pageManager.loadNewPage(mpw);
						}
						});
					}
				});

				lpw.getController().addEventListener(new GoToGamePageEventListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						pageManager.loadNewPage(gpw);
					}
				});
			}
		});

		clw.getController().addEventListener(new GoToLobbyEventListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LobbyPageView lpw = new LobbyPageView((Lobby) e.getSource());
				pageManager.loadNewPage(lpw);
				lpw.getController().addEventListener(new GoToMainPageEventListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						pageManager.loadNewPage(mpw);
					}
				});

				final GamePageView gpw = new GamePageView((Lobby) e.getSource());

				gpw.getController().addEventListener(new GoToScoreboardEventListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						ScoreboardPageView scw = new ScoreboardPageView((Lobby) e.getSource());
						pageManager.loadNewPage(scw);
				scw.getController().addEventListener(new GoToMainPageEventListener(){
							@Override
							public void actionPerformed(ActionEvent e) {
							pageManager.loadNewPage(mpw);
						}
						});
					}
				});

				lpw.getController().addEventListener(new GoToGamePageEventListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						pageManager.loadNewPage(gpw);
					}
				});
			}

		});

		clw.getController().addEventListener(new GoToMainPageEventListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pageManager.loadNewPage(mpw);
			}
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (Application.getUpdater() != null) {
					Application.getUpdater().close(true);
				}
				pageManager.onExit();
				System.exit(1);
			}
		});
	}

}
