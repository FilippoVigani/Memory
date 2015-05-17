package fvsl.memory.client.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import fvsl.memory.client.shell.Application;

public class PageManager implements ActionListener{

	public PageManager(JFrame container, Page rootPage) {
		this.rootPage = rootPage;
		this.container = container;
		loadNewPage(rootPage);
	}

	private Page rootPage;
	private Page currentPage;
	private JFrame container;

	public Page loadNewPage(Page page) {
		page.initialize();
		currentPage = page;
		Application.getUpdater().setPage(currentPage);
		container.setContentPane(page);
		container.pack();
		container.setSize(Application.WINDOW_WIDTH, Application.WINDOW_HEIGHT);
		return page;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	/**
	 * @return the rootPage
	 */
	public Page getRootPage() {
		return rootPage;
	}

	/**
	 * @param rootPage
	 *            the rootPage to set
	 */
	public void setRootPage(Page rootPage) {
		this.rootPage = rootPage;
	}

	/**
	 * @return the currentPage
	 */
	public Page getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            the currentPage to set
	 */
	public void setCurrentPage(Page currentPage) {
		this.currentPage = currentPage;
	}
}
