package fvsl.memory.client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

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
		if (currentPage == null)
			page.setLevel(0);
		else
			page.setLevel(currentPage.getLevel() + 1);
		page.setSourcePage(currentPage);
		currentPage = page;
		container.setContentPane(page);
		container.pack();
		return page;
	}

	public void loadPreviousPage() {
		if (!currentPage.isRoot()) {
			container.setContentPane(currentPage.getSourcePage());
			container.pack();
		}
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
