package fvsl.memory.client.Scoreboard;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import fvsl.memory.client.util.Page;

import java.awt.GridLayout;


public class ScoreboardPageView extends Page {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9192421170975614105L;

	public ScoreboardPageView(){
		super();
	}
	
	private JButton backButton;

	@Override
	protected void loadComponents() {
		// TODO Auto-generated method stub
		JPanel pan=new JPanel();
		add(pan);
		pan.setLayout(new GridLayout (1,1,0,50));
		JPanel pannelloSinistra=new JPanel();
		pannelloSinistra.setLayout(new GridLayout(8,1,0,0));
		pan.add(pannelloSinistra);
		JLabel giocatoriLabel= new JLabel("GIOCATORI");
		JLabel primoLabel= new JLabel("primo");
		JLabel secondoLabel= new JLabel("secondo");
		JLabel terzoLabel= new JLabel("terzo");
		JLabel quartoLabel= new JLabel("quarto");
		backButton= new JButton("Pagina iniziale"); 
		
		JPanel pannelloDestra=new JPanel();
		pannelloDestra.setLayout(new GridLayout(8,1,0,0));
		pan.add(pannelloDestra);
		JLabel punteggioLabel= new JLabel("PUNTEGGIO");
		JLabel score1Label= new JLabel("44");
		JLabel score2Label= new JLabel("33");
		JLabel score3Label= new JLabel("22");
		JLabel score4Label= new JLabel("11");
		
		pannelloSinistra.add(giocatoriLabel);
		pannelloSinistra.add(primoLabel);
		pannelloSinistra.add(secondoLabel);
		pannelloSinistra.add(terzoLabel);
		pannelloSinistra.add(quartoLabel);
		pannelloSinistra.add(backButton);
		
		pannelloDestra.add(punteggioLabel);
		pannelloDestra.add(score1Label);
		pannelloDestra.add(score2Label);
		pannelloDestra.add(score3Label);
		pannelloDestra.add(score4Label);
		

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

	@Override
	protected void bufferize(Object o) {
		// TODO Auto-generated method stub
		
	}
	
}