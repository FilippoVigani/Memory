package fvsl.memory.client.ui;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;


public class ScoreboardPageView extends Page {
	public ScoreboardPageView(){
		super();
	}
	
	private JButton backButton;

	@Override
	protected void loadComponents() {
		// TODO Auto-generated method stub
		JPanel pan=new JPanel();
		add(pan);
		JPanel pannello=new JPanel();
		pannello.setLayout(new GridLayout(0,1,0,50));
		pan.add(pannello);
		JLabel classificaLabel= new JLabel("CLASSIFICA");
		classificaLabel.setSize(100,100);
		JLabel primoLabel= new JLabel("primo");
		JLabel secondoLabel= new JLabel("secondo");
		JLabel terzoLabel= new JLabel("terzo");
		JLabel quartoLabel= new JLabel("quarto");
		backButton= new JButton("Pagina iniziale"); 
		
		pannello.add(classificaLabel);
		pannello.add(primoLabel);
		pannello.add(secondoLabel);
		pannello.add(terzoLabel);
		pannello.add(quartoLabel);
		pannello.add(backButton);
		

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