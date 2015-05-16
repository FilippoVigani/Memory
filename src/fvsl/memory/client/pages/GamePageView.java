package fvsl.memory.client.pages;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GamePageView extends Page {

	
	private JTable turnTable;
	@Override
	protected void bufferize(Object o) {
		// TODO Auto-generated method stub
		
	}
	private boolean turnButton=true;
	@Override
	protected void loadComponents() {
		// TODO Auto-generated method stub
		setSize(800,600);
		String[] colunmNames= {"giocatore","punteggio","pronto"};
		Object[][] data={
				{"stefano", "100",turnButton},
				{"io", "100",turnButton},
				{"podf", "100",turnButton}
		};
		JTable turnTable = new JTable(data,colunmNames);
		turnTable.setPreferredScrollableViewportSize(new Dimension(800,600));
		 
		
		JPanel pannello=new JPanel();
		add(pannello);
		pannello.setLayout(new BorderLayout());
		JPanel pannelloSinistra=new JPanel();
		pannello.add(pannelloSinistra,BorderLayout.WEST);
		JPanel	pannelloDestra= new JPanel();
		pannelloSinistra.setLayout(new BoxLayout(pannelloSinistra, BoxLayout.PAGE_AXIS));
		pannello.add(pannelloDestra,BorderLayout.CENTER);
		pannelloSinistra.add(Box.createRigidArea(new Dimension(50,210)));
		pannelloSinistra.add(turnTable);
		
		pannelloDestra.setLayout(new GridLayout(8,5,10,5));
		//pannelloDestra.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		for(int i=0;i<40;i++){
			JButton Button;
			pannelloDestra.add(Button=new JButton ("bottone"+i));
			Button.setSize(new Dimension(200,200));
		}
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