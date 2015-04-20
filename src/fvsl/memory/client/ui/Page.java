package fvsl.memory.client.ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public abstract class Page extends JPanel implements ActionListener{
	///Fields
	
	/**
	 * Represents the page this page was originated from.
	 */
	protected Page source;
	/**
	 * Represents the level of the node of the page. "0" if it is the root.
	 */
	protected int level;
	
	protected JFrame container;
	
	protected JButton previousPageButton;
	
	///Constructors
	
	/**
	 * Basic constructor for a root Page
	 */
	public Page(JFrame container){
		this.container = container;
		level = 0;
		previousPageButton = new JButton("Back");
		previousPageButton.addActionListener(this);
		loadComponents();
	}
	
	///Methods
	
	protected void loadDefaultComponents(){
		if (!isRoot()){
			add(previousPageButton);
		}
	}
	
	public Page loadNewPage(Page page){
		page.setLevel(this.getLevel() + 1);
		page.loadDefaultComponents();
		container.setContentPane(page);
		container.pack();
		return page;
	}
	
	public void loadPreviousPage(){
		if (!isRoot()){
			container.setContentPane(getSourcePage());
			container.pack();
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("afhsiudahi");
		if (e.getSource() == previousPageButton){
			loadPreviousPage();
		}
	}
}
