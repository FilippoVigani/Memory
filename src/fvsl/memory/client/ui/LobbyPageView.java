package fvsl.memory.client.ui;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JCheckBox;

public class LobbyPageView extends Page {

	public LobbyPageView(){
		super();
	}
	
	private JButton readyButton;
	private JCheckBox readyCB;
	
	
	@Override
	protected void loadComponents() {
		// TODO Auto-generated method stub
		JPanel pannello=new JPanel();
		add(pannello);
		pannello.setLayout(new GridLayout(6,2,55,55));
		
		readyButton= new JButton();
		readyCB= new JCheckBox();
		
		
		
	}

	@Override
	protected void setUpListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void populateViews() {
		// TODO Auto-generated method stub
		
	}
	
}