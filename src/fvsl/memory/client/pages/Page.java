package fvsl.memory.client.pages;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Page extends JPanel {
	// /Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4119387731560973667L;

	protected JFrame container;

	// /Constructors

	/**
	 * Basic constructor for a root Page
	 */
	public Page() {
		this(null);
	}

	public Page(Object o) {
		bufferize(o);
		loadComponents();
		setUpListeners();
		// updater = new GUIUpdaterRunnable(this);
		// updaterThread = new Thread(updater);
		// updaterThread.start();
	}

	// /Methods

	protected abstract void bufferize(Object o);

	protected abstract void loadComponents();

	protected abstract void setUpListeners();

	protected abstract void loadData();

	protected abstract void populateViews();

	protected abstract void onExit();
	
	public void initialize() {
		loadData();
		populateViews();
	}

}
