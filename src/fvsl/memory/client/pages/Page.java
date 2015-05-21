package fvsl.memory.client.pages;

import javax.swing.JFrame;
import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class Page.
 */
public abstract class Page extends JPanel {
	// /Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4119387731560973667L;

	protected JFrame container;

	// /Constructors

	/**
	 * Instantiates a new page.
	 */
	public Page() {
		this(null);
	}

	/**
	 * Instantiates a new page.
	 * 
	 * @param o
	 *            the o
	 */
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

	/**
	 * Initialize.
	 */
	public void initialize() {
		loadData();
		populateViews();
	}

}
