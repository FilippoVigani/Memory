package fvsl.memory.client.ui;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.*;

import java.awt.*;



public class LobbyPageView extends Page {

	public LobbyPageView(){
		super();
	}
	
	private JButton readyButton;
	private JButton backButton;
	private JCheckBox readyCB;
	private JCheckBox readyCB1;
	private JCheckBox readyCB2;

	
	@Override
	protected void loadComponents() {
		
		
		setLayout(new GridLayout(1, 0, 0, 0));
		// TODO Auto-generated method stub
		JPanel pannello=new JPanel();
		add(pannello);
		pannello.setLayout(new GridLayout(1,1,0,50));
		
		JPanel pannelloSinistra=new JPanel();
		//pannelloSinistra.setLayout(new BoxLayout(pannelloSinistra, BoxLayout.Y_AXIS));
		pannello.add(pannelloSinistra);
		JPanel	pannelloDestra= new JPanel();
		//pannelloDestra.setLayout(new BoxLayout(pannelloDestra, BoxLayout.Y_AXIS));
		pannello.add(pannelloDestra);
		pannelloSinistra.setLayout(new GridLayout(5,1,25,25));
		pannelloDestra.setLayout(new GridLayout(8,2,0,0));
		pannelloDestra.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		readyButton= new JButton("PRONTO");
		backButton= new JButton("torna indietro"); 
		readyCB= new JCheckBox();
		readyCB1= new JCheckBox();
		readyCB2= new JCheckBox();
		
		
		JLabel nomeStanzaLabel= new JLabel("nome stanza");
		JLabel nCoppieLabel= new JLabel("20 coppie");
		JLabel timerLabel= new JLabel("10 sec");
		JLabel giocatoreLabel= new JLabel("giocatore");
		JLabel g1Label= new JLabel("paolo");
		JLabel g2Label= new JLabel("francesco");
		JLabel g3Label= new JLabel("pollo");
		JLabel statoLabel= new JLabel("stato");
		
		g1Label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		g2Label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		g3Label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		statoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		giocatoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		pannelloSinistra.add(nomeStanzaLabel);
		pannelloSinistra.add(nCoppieLabel);
		pannelloSinistra.add(timerLabel);
		pannelloSinistra.add(readyButton);
		pannelloDestra.add(giocatoreLabel);
		pannelloDestra.add(statoLabel);
		pannelloDestra.add(g1Label);
		pannelloDestra.add(readyCB);
		pannelloDestra.add(g2Label);
		pannelloDestra.add(readyCB1);
		pannelloDestra.add(g3Label);
		pannelloDestra.add(readyCB2);
		pannelloDestra.add(backButton);
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