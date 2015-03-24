import javax.swing.JButton;
import javax.swing.JPanel;


public abstract class Page extends JPanel {
	///Fields
	
	/**
	 * Represents the page this page was originated from.
	 */
	protected Page source;
	/**
	 * Represents the level of the node of the page. "0" if it is the root.
	 */
	protected int level;
	
	protected JButton previousPageButton;
	
	///Constructors
	
	/**
	 * Basic constructor for a root Page
	 */
	public Page(){
		level = 0;
		loadDefaultComponents();
		loadComponents();
	}
	
	/**
	 * Basic constructor for a Page originated from another Page
	 * @param source The source page
	 */
	public Page(Page source) {
		level = source.getLevel()+1;
		this.source = source;
		loadDefaultComponents();
		loadComponents();
	}
	
	///Methods
	
	protected void loadDefaultComponents(){
		if (!isRoot()){
			previousPageButton = new JButton("Back");
			add(previousPageButton);
		}
	}
	
	protected abstract void loadComponents();
	
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
	
	/**
	 * @return whether this Page is root or not
	 */
	public final boolean isRoot(){
		return level == 0;
	}
	
	public final JButton getPreviousPageButton(){
		return previousPageButton;
	}
	
	
}
