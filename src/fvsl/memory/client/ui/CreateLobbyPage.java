package fvsl.memory.client.ui;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class CreateLobbyPage extends Page {

	public CreateLobbyPage(JFrame container){
		super(container);
	}
	
	@Override
	protected void loadComponents() {
		add(new JLabel("createLobbyPage"));

	}

}