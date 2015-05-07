package fvsl.memory.client.ui;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JButton;

import java.awt.FlowLayout;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JComboBox;


public class CreateLobbyPageView extends Page {

	public CreateLobbyPageView(){
		super();
	}

	private JButton creaButton;
	private	JButton backButton;
	
	@Override
	protected void loadComponents() {
		JPanel pannello=new JPanel();
		add(pannello);
		pannello.setLayout(new GridLayout(6,2,55,55));
		
		JTextField lobbyNameField=new JTextField("nome stanza");
		lobbyNameField.setColumns(10);
		
		
		JLabel nGiocatoriLabel= new JLabel("numero giocatori");
		
		JLabel nCoppieLabel= new JLabel("numero coppie");
		
		JLabel timerLabel= new JLabel("timer");
		
		JLabel passwordLabel= new JLabel("inserire Password");
		
		
		JTextField passwordField=new JTextField("passwords");
		passwordField.setColumns(10);
		
		String[] giocatoriPossibili={"2","3","4"};
		JComboBox nGCombo= new JComboBox(giocatoriPossibili);
		String[] coppiePossibili={"10","18","20"};
		JComboBox nCoppieCombo= new JComboBox(coppiePossibili);
		String[] timerPossibili={"5","10","15"};
		JComboBox timerCombo= new JComboBox(timerPossibili);
		
		creaButton= new JButton("Crea Stanza");
		backButton=new JButton("torna indietro");
		
		pannello.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		pannello.add(new JLabel("createLobbyPage"));
		pannello.add(lobbyNameField);
		pannello.add(nGiocatoriLabel);
		pannello.add(nGCombo);
		pannello.add(nCoppieLabel);
		pannello.add(nCoppieCombo);
		pannello.add(timerLabel);
		pannello.add(timerCombo);
		pannello.add(passwordLabel);
		pannello.add(passwordField);
		pannello.add(creaButton);
		pannello.add(backButton);
		
	
		
	}
	
	@Override
	protected void setUpListeners() {
		// TODO Auto-generated method stub
		/*backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                PageManager ciao=new PageManager();
                ciao.loadPreviousPage();
            }
        });*/
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
