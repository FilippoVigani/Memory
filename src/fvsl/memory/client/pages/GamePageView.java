package fvsl.memory.client.pages;

import javax.swing.BorderFactory;
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
import java.util.ArrayList;

import javax.swing.*;

import fvsl.memory.client.entities.Lobby;

public class GamePageView extends Page {
	public GamePageView(Lobby lobby){
		super(lobby);
	}
	private JTable turnTable;
	@Override
	protected void bufferize(Object o) {
		// TODO Auto-generated method stub
		
	}
	private boolean turnButton=true;
	@Override
	protected void loadComponents() {
		// TODO Auto-generated method stub

		boolean turnButton=true;
		String[] colunmNames= {"giocatore","punteggio","pronto"};
		Object[][] data={
				{"stefano", "100",turnButton},
				{"io", "100",turnButton},
				{"podf", "100",turnButton}
		};
		JTable turnTable = new JTable(data,colunmNames);
		JPanel pannello=new JPanel();
		add(pannello);
		pannello.setLayout(new BorderLayout());
		JPanel pannelloSinistra=new JPanel();
		pannello.add(pannelloSinistra,BorderLayout.WEST);
		JPanel	pannelloDestra= new JPanel();
		pannelloSinistra.setLayout(new BoxLayout(pannelloSinistra, BoxLayout.PAGE_AXIS));
		pannello.add(pannelloDestra,BorderLayout.CENTER);
		pannelloSinistra.add(Box.createRigidArea(new Dimension(0,210)));
		pannelloSinistra.add(turnTable);
		pannelloSinistra.add(Box.createRigidArea(new Dimension(0,300)));
		pannelloDestra.setLayout(new GridLayout(0,5,10,5));
		//pannelloDestra.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		for(int i=0;i<40;i++){
			JButton Button;
			pannelloDestra.add(Button=new JButton ("bottone"+i));
		}
		//pannelloDestra.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		ArrayList<String> list = new ArrayList<String>();
		list.add("ciao");
		list.add("pollo");
		list.add("pollicino");
		
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