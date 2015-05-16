package fvsl.memory.client.pages;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fvsl.memory.client.sockets.GUIUpdaterRunnable;


public abstract class Page extends JPanel {
	///Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4119387731560973667L;
	/**
	 * Represents the page this page was originated from.
	 */
	protected Page source;
	/**
	 * Represents the level of the node of the page. "0" if it is the root.
	 */
	protected int level;
	
	protected JFrame container;
	
	protected GUIUpdaterRunnable updater;
	protected Thread updaterThread;
	
	///Constructors
	
	/**
	 * Basic constructor for a root Page
	 */
	public Page(){
		level = 0;
		loadComponents();
		setUpListeners();
		loadData();
		populateViews();
		
		updater = new GUIUpdaterRunnable(this);
		updaterThread = new Thread(updater);
		updaterThread.start();
	}
	
	public Page(Object o){
		bufferize(o);
		level = 0;
		loadComponents();
		setUpListeners();
		loadData();
		populateViews();
		
		updater = new GUIUpdaterRunnable(this);
		updaterThread = new Thread(updater);
		updaterThread.start();
	}
	
	///Methods
	
	protected abstract void bufferize(Object o);
	protected abstract void loadComponents();
	protected abstract void setUpListeners();
	protected abstract void loadData();
	protected abstract void populateViews();
	
	///Getters and Setters
	
	/**
	 * @return the level of the page
	 */
	public final int getLevel() {
		return level;
	}
	
	/**
	 * @param level the level of the page to set
	 */
	protected final void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * @return the page this page originated from
	 */
	public final Page getSourcePage(){
		return source;
	}
	
	public void setSourcePage(Page page){
		source = page;
	}
	
	/**
	 * @return whether this Page is root or not
	 */
	public final boolean isRoot(){
		return level == 0;
	}

}
