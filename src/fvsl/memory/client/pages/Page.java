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
	/**
	 * Instantiates a new page.
	 */
	protected abstract void bufferize(Object o);
	
	/**
	 * Load Components to build the graphic view
	 */
	protected abstract void loadComponents();

	/**
	 * Instantiates all listeners  of the page trough the ControllerPage
	 */
	protected abstract void setUpListeners();

	/**
	 * Load all needed data of the page in the ModelPage
	 */
	protected abstract void loadData();

	/**
	 * Use model data to populate the ViewPage
	 */
	protected abstract void populateViews();
	
	/**
	 * Control the window exit 
	 */
	protected abstract void onExit();

	/**
	 * Initialize.
	 */
	public void initialize() {
		loadData();
		populateViews();
	}

}
