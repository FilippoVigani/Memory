package fvsl.memory.client.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import fvsl.memory.client.shell.Application;

// TODO: Auto-generated Javadoc
/**
 * The Class PageManager. Control the switch of various pages.
 * 
 * @author Filippo Vigani
 *
 */
public class PageManager implements ActionListener {

	/**
	 * Instantiates a new page manager.
	 * 
	 * @param container
	 *            the container
	 * @param rootPage
	 *            the root page
	 */
	public PageManager(JFrame container, Page rootPage) {
		this.rootPage = rootPage;
		this.container = container;
		loadNewPage(rootPage);
	}

	private Page rootPage;
	private Page currentPage;
	private JFrame container;

	/**
	 * Load new page.
	 * 
	 * @param page
	 *            the page
	 * @return the page
	 */
	public Page loadNewPage(Page page) {
		page.initialize();
		currentPage = page;
		Application.getUpdater().setPage(currentPage);
		container.setContentPane(page);
		container.pack();
		container.setSize(Application.WINDOW_WIDTH, Application.WINDOW_HEIGHT);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	/**
	 * Gets the root page.
	 * 
	 * @return the root page
	 */
	public Page getRootPage() {
		return rootPage;
	}

	/**
	 * Sets the root page.
	 * 
	 * @param rootPage
	 *            the new root page
	 */
	public void setRootPage(Page rootPage) {
		this.rootPage = rootPage;
	}

	/**
	 * Gets the current page.
	 * 
	 * @return the current page
	 */
	public Page getCurrentPage() {
		return currentPage;
	}

	/**
	 * Sets the current page.
	 * 
	 * @param currentPage
	 *            the new current page
	 */
	public void setCurrentPage(Page currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * On exit.
	 */
	public void onExit() {
		Application.getUpdater().setRunning(false);
		currentPage.onExit();
	}
}
