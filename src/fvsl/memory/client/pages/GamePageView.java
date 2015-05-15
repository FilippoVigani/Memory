package fvsl.memory.client.pages;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

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

	@Override
	protected void loadComponents() {
		// TODO Auto-generated method stub
		JPanel pannello=new JPanel();
		add(pannello);
		pannello.setLayout(new GridLayout(1,1,0,50));
		JPanel pannelloSinistra=new JPanel();
		pannello.add(pannelloSinistra);
		JPanel	pannelloDestra= new JPanel();
		pannello.add(pannelloDestra);
		turnTable = new JTable();
		pannelloSinistra.add(turnTable);
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